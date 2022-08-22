package iss.team5.vms.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import iss.team5.vms.DTO.ResponsePojo;
import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.helper.DateHelper;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.repositories.BookingRepo;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepo br;

	@Autowired
	RoomService rms;

	@Override
	public List<Booking> findAllBookings() {
		return br.findAll();
	}

	@Override
	public Booking findBookingById(String id) {
		return br.findById(id).get();
	}

	@Override
	@Transactional
	public Booking createBooking(Booking booking) {
		return br.saveAndFlush(booking);
	}

	@Override
	public List<Booking> findBookingsByRoom(Room room) {
		return br.findAllByRoom(room);
	}

	@Override
	public boolean tableExist() {
		return br.existsBy();
	}

	@Override
	public List<Booking> findBookingsByStudent(Student student) {
		List<Booking> bookings = br.findAllBookingByStudent(student);
		Collections.reverse(bookings);
		return bookings;
	}

	@Override
	public void addStudent(Booking booking, Student student) {
		booking.setStudent(student);
		br.saveAndFlush(booking);

	}

	@Override
	public void addRoom(Booking booking, Room room) {
		booking.setRoom(room);
		br.saveAndFlush(booking);
	}

	@Override
	public String checkIn(Student student, Booking booking) {
		if (booking.getStudent().getId() == student.getId()) {
			if (booking.getTime().plusMinutes(10).compareTo(LocalTime.now()) > 0) {
				booking.setCheckedIn(true);
				br.saveAndFlush(booking);
				return "Checked in successfully";
			} else
				return "Time now is past check in window period";
		} else
			return "Booking owner mismatch";
	}

	@Transactional
	@Override
	public void cancelCourseById(String id) {
		Booking booking = br.findById(id).get();
		if (booking != null) {
			booking.setStatus(BookingStatus.CANCELLED);
			br.saveAndFlush(booking);
		} else
			throw new NullPointerException();
	}

	@Override
	public List<Booking> findBookingsAvailableExact(Booking booking, List<Room> rooms, Student student) {
		int duration = booking.getDuration();
		if (duration > 180) {
			duration = 180;
		}
		if (predictPeak(booking)&&duration>60) {
			duration = 60;
		}
		List<Room> frooms = rms.findAllRoomsOpenForBooking(booking, rooms);

		List<Booking> bookings = new ArrayList<Booking>();
		// if existing bookings for a room for the same day as requested booking does
		// not overlap, add booking for room as an option
		for (Room r : frooms) {
			if (!checkBookingByDateTimeRoom(booking, r)) {
				bookings.add(new Booking(r.getRoomName(), booking.getDate(), booking.getTime(), duration, r));
			}
		}

		// filtering bookings to be offered against student's existing bookings so there
		// are no overlaps
		List<Booking> sbookings = findStudentBookingsForDate(student, booking.getDate());
		for (Booking sbooking : sbookings) {
			bookings = bookings.stream().filter(b -> !checkBookingsOverlap(b, sbooking)).collect(Collectors.toList());
		}
		return bookings;

	}

	@Override
	public List<Booking> findBookingsAvailableAlternative(Booking booking, List<Room> rooms, Student student) {
		List<Booking> bookings = new ArrayList<Booking>();
		int duration = booking.getDuration();
		if (duration > 180) {
			duration = 180;
		}
		if (predictPeak(booking)&&duration>60) {
			duration = 60;
		}
		List<Room> frooms = rms.findAllRoomsOpenForBooking(booking, rooms);
		Booking bookingBefore1 = new Booking("placeholder", booking.getDate(), booking.getTime(), duration);
		Booking bookingBefore2 = new Booking("placeholder", booking.getDate(), booking.getTime(), duration);
		Booking bookingAfter1 = new Booking("placeholder", booking.getDate(), booking.getTime(), duration);
		Booking bookingAfter2 = new Booking("placeholder", booking.getDate(), booking.getTime(), duration);

		for (Room r : frooms) {
			Booking overlapBooking = findOverlapBookingByDateTimeRoom(booking, r);
			if (overlapBooking != null) {
				// same start time, reduced duration
				bookingBefore1.setDuration(
						Math.toIntExact(booking.getTime().until(overlapBooking.getTime(), ChronoUnit.MINUTES)));
				if (bookingBefore1.getDuration() >= 30 && !checkBookingByDateTimeRoom(bookingBefore1, r)) {
					bookings.add(new Booking(r.getRoomName(), bookingBefore1.getDate(), bookingBefore1.getTime(),
							bookingBefore1.getDuration(), r));
				}

				// earlier start time, same duration
				bookingBefore2.setTime(overlapBooking.getTime().minusMinutes(booking.getDuration() + 5));
				if (!checkBookingByDateTimeRoom(bookingBefore2, r)) {
					bookings.add(new Booking(r.getRoomName(), bookingBefore2.getDate(), bookingBefore2.getTime(),
							bookingBefore2.getDuration(), r));
				}

				// later start time, same duration
				bookingAfter1.setTime(overlapBooking.getTime().plusMinutes(overlapBooking.getDuration() + 5));

				if (bookingAfter1.getDuration() >= 30 && !checkBookingByDateTimeRoom(bookingAfter1, r)) {
					bookings.add(new Booking(r.getRoomName(), bookingAfter1.getDate(), bookingAfter1.getTime(),
							bookingAfter1.getDuration(), r));
				}

				// later start time, reduced duration
				bookingAfter2.setTime(overlapBooking.getTime().plusMinutes(overlapBooking.getDuration() + 5));
				bookingAfter2.setDuration(Math.toIntExact(booking.getDuration() - booking.getTime().until(
						overlapBooking.getTime().plusMinutes(overlapBooking.getDuration()), ChronoUnit.MINUTES)));

				if (bookingAfter2.getDuration() >= 30 && !checkBookingByDateTimeRoom(bookingAfter2, r)) {
					bookings.add(new Booking(r.getRoomName(), bookingAfter2.getDate(), bookingAfter2.getTime(),
							bookingAfter2.getDuration(), r));
				}
			}
		}

		List<Booking> sbookings = findStudentBookingsForDate(student, booking.getDate());
		// filtering bookings to be offered against student's existing bookings so there
		// are no overlaps or start time before now
		for (Booking sbooking : sbookings) {
			bookings = bookings.stream().filter(b -> !checkBookingsOverlap(b, sbooking))
					.filter(b -> !b.getTime().isBefore(LocalTime.now())).collect(Collectors.toList());
		}

		return bookings;
	}

	@Override
	public boolean checkBookingsOverlap(Booking newBooking, Booking existingBooking) {
		if (newBooking.getTime().isBefore(existingBooking.getTime().plusMinutes(existingBooking.getDuration()))
				&& (newBooking.getTime().plusMinutes(newBooking.getDuration()).isAfter(existingBooking.getTime()))
				&& (existingBooking.getStatus().equals(BookingStatus.SUCCESSFUL))) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkBookingByDateTimeRoom(Booking booking, Room room) {
		// all the bookings for the day for the specific room
		List<Booking> bookings = br.findByDateAndRoom(booking.getDate(), room);
		// check whether each booking for the room overlaps with the requested booking
		// time. if overlap return false
		for (Booking b : bookings) {
			if (checkBookingsOverlap(booking, b)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Booking findOverlapBookingByDateTimeRoom(Booking booking, Room room) {
		// all the bookings for the day for the specific room
		List<Booking> bookings = br.findByDateAndRoom(booking.getDate(), room);
		// check whether each booking for the room overlaps with the requested booking
		// time. if overlap return false
		for (Booking b : bookings) {
			if (checkBookingsOverlap(booking, b)) {
				return b;
			}
		}
		return null;
	}

	@Override
	public void scheduleWaitingList(Booking booking, Room room) {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		Runnable setStatus = () -> {
			if (!checkBookingByDateTimeRoom(booking, room)) {
				booking.setStatus(BookingStatus.SUCCESSFUL);
				createBooking(booking);
			} else{
				booking.setStatus(BookingStatus.REJECTED);
				createBooking(booking);
			}

		};

		executorService.schedule(setStatus, 1, TimeUnit.MINUTES);
	}

	@Override
	public List<Booking> updateBookingInProgress(List<Booking> bookings) {
		bookings.stream().filter(b -> b.getDate().equals(LocalDate.now()))
				.filter(b -> b.getTime().isBefore(LocalTime.now())
						&& b.getTime().plusMinutes(b.getDuration()).isAfter(LocalTime.now()))
				.filter(b -> b.getStatus().equals(BookingStatus.SUCCESSFUL)).forEach(b -> b.setBookingInProgress(true));

		bookings.stream()
				.filter(b -> !b.getDate().equals(LocalDate.now()) || b.getTime().isAfter(LocalTime.now())
						|| b.getTime().plusMinutes(b.getDuration()).isBefore(LocalTime.now()))
				.forEach(b -> b.setBookingInProgress(false));
		
		bookings.stream().forEach(b->b.setValidCancel(false));
		
		bookings.stream().filter(b->b.getStatus().equals(BookingStatus.SUCCESSFUL) && ((b.getDate().isAfter(LocalDate.now())
				|| (b.getDate().equals(LocalDate.now()) && b.getTime().isAfter(LocalTime.now()))))).forEach(b->b.setValidCancel(true));

		return bookings;
	}

	@Override
	public Booking findStudentCurrentBooking(Student student) {
		List<Booking> bookings = br.findAllBookingByStudent(student);
		bookings = updateBookingInProgress(bookings);
		return bookings.stream().filter(b -> b.isBookingInProgress()).findFirst().get();
	}

	@Override
	public List<Booking> findStudentBookingsForDate(Student student, LocalDate date) {
		List<Booking> bookings = br.findByDateAndStudent(date, student);
		return bookings;
	}

	@Override
	public Booking findBookingBefore(Booking booking) {
		Room room = booking.getRoom();
		String id = booking.getId();
		LocalDate date = booking.getDate();
		List<Booking> bookingList = br.findByDateAndRoom(date, room);
		for (int i = 0; i < bookingList.size(); i++) {
			Booking booking1 = bookingList.get(i);
			if (booking1.getId().equals(id)) {
				booking = bookingList.get(i - 1);
				break;
			}
		}
		return booking;
	}

	@Override
	public Booking findStudentNextBooking(Student student) {
		List<Booking> bookingsAfter = br.findByDateAfterAndStudent(LocalDate.now(), student);
		List<Booking> bookingsFromToday = findStudentBookingsForDate(student, LocalDate.now());
		bookingsFromToday = bookingsFromToday.stream().filter(b -> b.getStatus().equals(BookingStatus.SUCCESSFUL))
				.filter(b -> b.getTime().isAfter(LocalTime.now())).collect(Collectors.toList());
		bookingsAfter = bookingsAfter.stream().filter(b -> b.getStatus().equals(BookingStatus.SUCCESSFUL))
				.collect(Collectors.toList());
		bookingsFromToday.addAll(bookingsAfter);
		Collections.sort(bookingsFromToday, new Comparator<Booking>() {
			public int compare(Booking b1, Booking b2) {
				if (b1.getDate() == null || b2.getDate() == null)
					return 0;
				return b1.getDate().compareTo(b2.getDate());
			}
		});

		return bookingsFromToday.get(0);
	}

	@Override
	public boolean predictHogging(String imgPath) {
		String uri = "http://127.0.0.1:5000/hogpredict?filename=" + imgPath;
		RestTemplate restTemplate = new RestTemplate();
		ResponsePojo response = restTemplate.getForObject(uri, ResponsePojo.class);
		String result = response.getResponse();
		System.out.println(result);
		if (result.equals("TRUE")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean predictPeak(Booking booking) {

		LocalDate date = booking.getDate();
		int week = date.get(WeekFields.ISO.weekOfWeekBasedYear());

		List<Booking> pastWeekBookings = findBookingsInCurrentWeek(booking.getDate().minusDays(6));

		int volume = (int) pastWeekBookings.stream().count();

		String uri = "http://127.0.0.1:5000/peakpredict?week=" + week + "&volume=" + volume;
		RestTemplate restTemplate = new RestTemplate();
		ResponsePojo response = restTemplate.getForObject(uri, ResponsePojo.class);
		String result = response.getResponse();
		System.out.println(result);
		if (result.equals("TRUE")) {
			return true;
		}
		return false;
	}

	@Override
	public List<Booking> findBookingsInCurrentWeek(LocalDate date) {
		
		LocalDate firstDayOfWeek = DateHelper.FirstDayOfDateWeek(date);
		return br.findByDateBetween(firstDayOfWeek, firstDayOfWeek.plusDays(6));

	}

	@Override
	public int getBookingStatusCounts(List<Booking> bookings, BookingStatus status) {
		long count = bookings.stream().filter(b -> b.getStatus().equals(status)).count();
		return (int) count;
	}

	@Override
	public Integer getSuccessBookingsDurationForDate(List<Booking> bookings, LocalDate date) {
		int minutes = bookings.stream().filter(b -> b.getStatus().equals(BookingStatus.SUCCESSFUL) && b.getDate().equals(date))
				.map(Booking::getDuration).mapToInt(Integer::intValue).sum();
		return minutes/60;
	}
	@Override
	public int getBookingCountsForRoom(List<Booking> bookings, Room room) {
		long count = bookings.stream().filter(b -> b.getRoom().equals(room)).count();
		return (int) count;
	}
	
	@Override
	public Integer getBookingHoursForRoom(List<Booking> bookings, Room room) {
		int minutes = bookings.stream().filter(b -> b.getRoom().equals(room))
				.map(Booking::getDuration).mapToInt(Integer::intValue).sum();

			return minutes/60;

	}

	@Override
	public List<Booking> findTodayAndUpcomingBookings() {
		LocalDate date = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		LocalTime time = LocalTime.of(currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
		return br.findAllBookingsAfterDateTime(date, time);
	}

}
