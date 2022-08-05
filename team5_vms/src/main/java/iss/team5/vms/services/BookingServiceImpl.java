package iss.team5.vms.services;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.repositories.BookingRepo;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepo br;

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
		return br.findAllBookingsByStudent(student);
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
		if (booking.getStudent().getId().equals(student.getId())) {
			if (booking.getTime().plusMinutes(10).compareTo(LocalTime.now()) > 0) {
				booking.setCheckedIn(true);
				br.saveAndFlush(booking);
				return "Checked in successfully";
			} else
				return "Time now is past check in window period";
		}
		else
			return "Booking owner mismatch";
	}
	
	@Transactional
	@Override
	public void cancelCourseById(String id) {
		Booking booking = br.findById(id).get();
		if (booking != null) {
		booking.setStatus(BookingStatus.CANCELLED);
		br.saveAndFlush(booking);
		}
		else throw new NullPointerException();
	}

	@Override
	public List<Booking> checkBookingAvailable(Booking booking, List<Room> rooms) {
		LocalTime bstart = booking.getTime();
		int duration = booking.getDuration();
		if (duration>2) duration=2;
		LocalTime bend = bstart.plusHours(duration);
		
		//rooms with no blocked timings
		List<Room> nullBlockTimeRooms = rooms.stream().filter(room -> room.getBlockedStartTime()==null).collect(Collectors.toList());
		
		//check against room blocked timings if there are
		List<Room> frooms = rooms.stream()
				.filter(room -> room.getBlockedStartTime()!=null)
				.filter(room -> (room.getBlockedStartTime().isAfter(bend))
						&& (room.getBlockedStartTime().plusHours(room.getBlockDuration()).isBefore(bstart)))
				.collect(Collectors.toList());
		
		//all rooms that are available in requested timing
		frooms.addAll(nullBlockTimeRooms);
		
		List<Booking> bookings = new ArrayList<Booking>();
		//checking if there are existing bookings overlapping with requested time slot for each room for that day
		for (Room r : frooms) {
			if (checkBookingByDateTimeRoom(booking, r)) {
				
				bookings.add(new Booking(r.getRoomName(), booking.getDate(), booking.getTime(), duration, r));
			}
		}
		return bookings;
	}

	@Override
	public boolean checkBookingByDateTimeRoom(Booking booking, Room room) {
		// all the bookings for the day for the specific room
		List<Booking> bookings = br.findByDateAndRoom(booking.getDate(), room);
		// check whether each booking for the room overlaps with the requested booking time. if overlap return false
		for (Booking b : bookings) {
			if (booking.getTime().isBefore(b.getTime().plusHours(b.getDuration()))
					&& (booking.getTime().plusHours(booking.getDuration()).isAfter(b.getTime()))) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void scheduleWaitingList(Booking booking, Room room)
	{
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		Runnable setStatus = () -> {if (checkBookingByDateTimeRoom(booking,room))
			{booking.setStatus(BookingStatus.SUCCESSFUL);
			createBooking(booking);}
		else
			booking.setStatus(BookingStatus.REJECTED);};

		executorService.schedule(setStatus, 1, TimeUnit.HOURS);
	}

}
