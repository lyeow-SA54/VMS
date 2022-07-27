package iss.team5.vms.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;

public interface BookingRepo extends JpaRepository<Booking,String>{
	
	Boolean existsBy();
	
	ArrayList<Booking> findAllBookingsByRoom(Room room);
	
	Booking findBookingByStudent(Student student);	
	
	ArrayList<Booking> findAllBookingByStudent(Student student);

	List<Booking> findBookingByRoom(Room room);
	
	//Student class doesn't have Booking attribute, use BookingRepo to do this instead
//	Student findStudentByBooking(Booking booking);

	ArrayList<Booking> findAllById(String id);
	
}
