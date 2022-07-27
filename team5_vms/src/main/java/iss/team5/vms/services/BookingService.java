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
	
	ArrayList<Room> findAvailableRoom(Booking booking);

}
