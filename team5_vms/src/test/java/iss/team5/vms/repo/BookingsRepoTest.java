package iss.team5.vms.repo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import iss.team5.vms.Team5VmsApplication;
import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.helper.dateTimeInput;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.repositories.BookingRepo;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Team5VmsApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace =
AutoConfigureTestDatabase.Replace.NONE)

public class BookingsRepoTest {

	@Autowired BookingRepo br;
	
	@Test
    @Order(1)
	public void testCreateBooking()  {
    	List<Booking> bookingList = new ArrayList<Booking>();
    	bookingList.add(new Booking("B1", dateTimeInput.dateInput("01/08/2022"), LocalTime.now(), 1, BookingStatus.SUCCESSFUL));
    	bookingList.add(new Booking("B2", dateTimeInput.dateInput("02/08/2022"), LocalTime.now(), 2, BookingStatus.CANCELLED));
		br.saveAllAndFlush(bookingList);
		Booking b1 = bookingList.get(0);
		Assertions.assertNotNull(b1.getId());
    }
	
	@Test
    @Order(2)
	public void testUpdateBooking()  {
    	Booking b1 = new Booking("B1", dateTimeInput.dateInput("01/08/2022"), LocalTime.now(), 1, BookingStatus.SUCCESSFUL);
		br.saveAndFlush(b1);
		b1.setRoom(new Room("R1"));
		Assertions.assertNotNull(b1.getRoom());
    }
	
	@Test
    @Order(3)
	public void findBookingByRoom()  {
    	Booking b1 = new Booking("B1", dateTimeInput.dateInput("01/08/2022"), LocalTime.now(), 1, BookingStatus.SUCCESSFUL);
		br.saveAndFlush(b1);
		Room r1 = new Room("R1");
		b1.setRoom(r1);
		List<Booking> bookinglist = br.findAllBookingsByRoom(r1);
		Assertions.assertNotNull(bookinglist.size());
    }
	
	@Test
	@Order(4)
	public void findBookingById() {
		Booking b1 = new Booking ("B1", dateTimeInput.dateInput("01/08/2022"), LocalTime.now(), 1, BookingStatus.SUCCESSFUL);
		br.saveAndFlush(b1);
		Assertions.assertNotNull(b1.getId());
	}
	
	
	  @Test
	  @Order(5) 
	  public void testAddStudentToBooking() { 
	  Booking b1 = new Booking ("B1", dateTimeInput.dateInput("01/08/2022"), LocalTime.now(), 1,BookingStatus.SUCCESSFUL); 
	  br.saveAndFlush(b1);
	  Student s1= new Student("S1");
	  b1.setStudent(s1);
	  List<Booking>bookinglist = br.findAllBookingsByStudent(s1);
	  Assertions.assertNotNull(bookinglist.size()); 
	 }
	  
	 @Test
	 @Order(6)
	 public void testAddRoomToBooking() {
		 Booking b1 = new Booking ("B1", dateTimeInput.dateInput("01/08/2022"), LocalTime.now(), 1,BookingStatus.SUCCESSFUL); 
		 br.saveAndFlush(b1);
		 Room r1 = new Room("R1");
		 b1.setRoom(r1);
		 List<Booking>bookinglist = br.findAllBookingsByRoom(r1);
		 Assertions.assertNotNull(bookinglist.size()); 
	 }
	 
	 // @Test
	//  @Order(6)
	 // public void testcheckIn() {
		  //Booking b1 = new Booking ("B1", dateTimeInput.dateInput("01/08/2022"), LocalTime.now(), 1,BookingStatus.SUCCESSFUL); 
		 // br.saveAndFlush(b1);
		 // Student s1= new Student("S1");
		 // b1.setStudent(s1);
		 // List<Booking>bookinglist = br.findAllBookingsByStudent(s1);
		  //bookinglist.getStudent().getId().equals(student.getId())) {
				//if (booking.getTime().plusMinutes(10).compareTo(LocalTime.now()) > 0) {
					//booking.setCheckedIn(true);
					//br.saveAndFlush(booking);
	  //}
	 
	 
//	 @Test
//	 @Order(7)
//	 public void testcancelCourseById() {
//	 	Booking booking = br.findById(id).get();
//		if (booking != null) {
//		booking.setStatus(BookingStatus.CANCELLED);
//		br.saveAndFlush(booking);
//		}
//		Assertions.assertNull(booking.getId());
//		 
//	 }
//	 
//	 @Test
//	 @Order(8)
//	 public void testcheckBookingAvailable(){
//		 
//	 }
//	 
//	 @Test
//	 @Order(9)
//	 public void testcheckBookingByDateTimeRoom() {
//		 
//	 }
//	 
//	 @Test
//	 @Order(10)
//	 public void testscheduleWaitingList() {
//		 
//	 }
	 
	
}
