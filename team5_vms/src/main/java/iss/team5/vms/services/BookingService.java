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

	Booking changeBooking(Booking booking);

	void removeBooking(Booking booking);

	List<Booking> findBookingsByRoom(Room room);	
	
	ArrayList<Booking> findAllBookingsByRoom(Room room);
	
	Booking findBookingByStudent(Student student);	
	
	ArrayList<Booking> findAllBookingByStudent(Student student);

	Room findRoomByBooking(Booking booking);
	
	Student findStudentByBooking(Booking booking);

}
