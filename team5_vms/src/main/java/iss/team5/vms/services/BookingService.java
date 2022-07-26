package iss.team5.vms.services;

import java.util.ArrayList;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Student;
import iss.team5.vms.model.Room;

public interface BookingService {
	
	ArrayList<Booking> findAllBookings();
	
	Booking findBooking(String id);

	Booking createBooking(Booking booking);

	Booking changeBooking(Booking booking);

	void removeBooking(Booking booking);

	Booking findBookingByRoom(Room room);	
	
	ArrayList<Booking> findAllBookingsByRoom(Room room);
	
	Booking findBookingByStudent(Student student);	
	
	ArrayList<Booking> findAllBookingByStudent(Student student);

	Room findRoomByBooking(Booking booking);
	
	Student findStudentByBooking(Booking booking);

}
