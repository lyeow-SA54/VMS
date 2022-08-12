package iss.team5.vms.services;

import java.util.List;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;

public interface BookingService {
	
	boolean tableExist();
	
	boolean checkBookingByDateTimeRoom(Booking booking, Room room);
	
	boolean predictHogging(String imgPath);
	
	boolean checkBookingsOverlap(Booking newBooking, Booking existingBooking);
	
	Booking findBookingById(String id);

	Booking createBooking(Booking booking);
			
	Booking findStudentCurrentBooking(Student student);
	
	Booking findBookingBefore(Booking booking);
	
	List<Booking> findAllBookings();

	List<Booking> findBookingsByRoom(Room room);	
	
	List<Booking> findBookingsByStudent(Student student);
	
	List<Booking> checkBookingInProgress(List<Booking> bookings);
	
	List<Booking> checkBookingAvailable(Booking booking, List<Room> rooms);
	
	String checkIn(Student student, Booking booking);

	void cancelCourseById(String id);

	void scheduleWaitingList(Booking booking, Room room);
	
	void addStudent(Booking booking, Student student);
	
	void addRoom(Booking booking, Room room);



}
