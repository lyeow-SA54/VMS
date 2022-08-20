package iss.team5.vms.services;

import java.time.LocalDate;
import java.util.List;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;

public interface BookingService {
	
	boolean tableExist();
	
	boolean checkBookingByDateTimeRoom(Booking booking, Room room);
	
	boolean predictHogging(String imgPath);

	boolean predictPeak(Booking booking);
	
	boolean checkBookingsOverlap(Booking newBooking, Booking existingBooking);

	Booking createBooking(Booking booking);
	
	Booking findBookingById(String id);
			
	Booking findStudentCurrentBooking(Student student);
	
	Booking findStudentNextBooking(Student student);
	
	Booking findBookingBefore(Booking booking);
	
	Booking findOverlapBookingByDateTimeRoom(Booking booking, Room room);
	
	List<Booking> findAllBookings();

	List<Booking> findBookingsByRoom(Room room);	
	
	List<Booking> findBookingsByStudent(Student student);
	
	List<Booking> findStudentBookingsForDate(Student student, LocalDate date);
	
	List<Booking> findBookingsAvailableExact(Booking booking, List<Room> rooms, Student student);
	
	List<Booking> findBookingsAvailableAlternative(Booking booking, List<Room> rooms, Student student);
	
	List<Booking> findBookingsInCurrentWeek(LocalDate date);
	
	List<Booking> updateBookingInProgress(List<Booking> bookings);

	String checkIn(Student student, Booking booking);
	
	int getBookingStatusCounts(List<Booking> bookings, BookingStatus status);

	Integer getSuccessBookingsDurationForDate(List<Booking> bookings, LocalDate date);
	
	Integer getBookingHoursForRoom(List<Booking> bookings, Room room);

	void cancelCourseById(String id);

	void scheduleWaitingList(Booking booking, Room room);
	
	void addStudent(Booking booking, Student student);
	
	void addRoom(Booking booking, Room room);

	int getBookingCountsForRoom(List<Booking> bookings, Room room);
	
	List<Booking> findTodayAndUpcomingBookings();

	void removeBooking(Booking booking);

}
