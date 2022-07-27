package iss.team5.vms.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;

public interface BookingRepo extends JpaRepository<Booking,String>{
	Booking findBookingByRoom(Room room);
	ArrayList<Booking> findAllBookingsByRoom(Room room);
	ArrayList<Booking> findAllBookingByStudent(Student student);
	Room findRoomByBooking(Booking booking);
	Student findStudentByBooking(Booking booking);
	
}
