package iss.team5.vms.services;

import java.util.ArrayList;
import java.util.List;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Student;
import iss.team5.vms.model.Room;

public interface BookingService {
	
	boolean tableExist();
	
	List<Booking> findAllBookings();
	
	Booking findBookingById(String id);

	Booking createBooking(Booking booking);

	List<Booking> findBookingsByRoom(Room room);	
	
	List<Booking> findBookingsByStudent(Student student);
	
	void addStudent(Booking booking, Student student);
	
	void addRoom(Booking booking, Room room);
	
	String checkIn(Student student, Booking booking);
	
	List<Booking> checkBookingAvailable(Booking booking, List<Room> rooms);

	boolean checkBookingByDateTimeRoom(Booking booking, Room room);

	void cancelCourseById(String id);

	void scheduleWaitingList(Booking booking, Room room);

	List<Booking> checkBookingInProgress(List<Booking> bookings);

	Booking findStudentCurrentBooking(Student student);
	
	Booking findLastBooking(Booking booking);

}
