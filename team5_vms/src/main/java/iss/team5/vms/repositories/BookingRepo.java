package iss.team5.vms.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;

public interface BookingRepo extends JpaRepository<Booking,String>{
	
	Boolean existsBy();
	
	List<Booking> findAllBookingsByRoom(Room room);
	
	List<Booking> findAllBookingByStudent(Student student);
	
	List<Booking> findAllByDate(LocalDate date);

	List<Booking> findAllByRoom(Room room);
	
	List<Booking> findByDateAndRoom(LocalDate date, Room room);
	
	List<Booking> findByDateAndStudent(LocalDate date, Student student);
	
	List<Booking> findByDateBetween(LocalDate start, LocalDate end);
	
	List<Booking> findByDateAfterAndStudent(LocalDate date, Student student);

	@Query("SELECT b FROM Booking b WHERE b.date >= :date AND b.time >= :time AND (b.status='SUCCESSFUL' OR b.status='WAITINGLIST')")
	public List<Booking> findAllBookingsByDateTime(@Param("date") LocalDate date, @Param("time") LocalTime time);
	
}
