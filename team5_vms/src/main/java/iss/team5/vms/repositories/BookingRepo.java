package iss.team5.vms.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Student;
import iss.team5.vms.model.Room;

public interface BookingRepo extends JpaRepository<Booking,String>{
	
	ArrayList<Booking> findAllBookings();
	
	ArrayList<Booking> findAllBookingsByRoom(Room room);
	
	Booking findBookingByStudent(Student student);	
	
	ArrayList<Booking> findAllBookingByStudent(Student student);

	Room findRoomByBooking(Booking booking);
	
	Student findStudentByBooking(Booking booking);

}
