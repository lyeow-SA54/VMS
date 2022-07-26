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
import iss.team5.vms.repositories.StudentRepo;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Team5VmsApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace =
AutoConfigureTestDatabase.Replace.NONE)

public class BookingsRepoTest {

	@Autowired BookingRepo br;
	@Autowired StudentRepo sr;
	
	@Test
    @Order(1)
	public void testCreateBooking()  {
    	List<Booking> bookingList = new ArrayList<Booking>();
    	bookingList.add(new Booking("B99", dateTimeInput.dateInput("01/08/2029"), LocalTime.now(), 1, BookingStatus.SUCCESSFUL));
    	bookingList.add(new Booking("B98", dateTimeInput.dateInput("02/08/2029"), LocalTime.now(), 2, BookingStatus.CANCELLED));
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
	public void findBookingByStudent()  {
    	Booking b1 = new Booking("B1", dateTimeInput.dateInput("01/08/2022"), LocalTime.now(), 1, BookingStatus.SUCCESSFUL);
		br.saveAndFlush(b1);
		Student s1 = new Student("first", "last", "email", "user", 7);
		sr.saveAndFlush(s1);
		b1.setStudent(s1);
		List<Booking> bookinglist = br.findAllBookingByStudent(s1);
		Assertions.assertNotNull(bookinglist.size());
    }
	
}
