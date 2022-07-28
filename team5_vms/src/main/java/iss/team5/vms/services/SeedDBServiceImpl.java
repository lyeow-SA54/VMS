package iss.team5.vms.services;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.helper.Category;
import iss.team5.vms.helper.StudentStatus;
import iss.team5.vms.helper.dateTimeInput;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Report;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.model.User;

@Service
public class SeedDBServiceImpl implements SeedDBService {

	@Autowired 
	BookingService bs;
	
	@Autowired 
	ReportService rs;
	
	@Autowired 
	RoomService rms;
	
	@Autowired 
	StudentService ss;
	
	@Autowired 
	UserService us;
	
	public void databaseInit() {
		createInitialData();
		loadBookingData();
	}

	public void createInitialData() {
		if(!ss.tableExist()){
		ss.createStudent(new Student("fn1", "email1@u.nus.edu", "user1", "password1"));
		ss.createStudent(new Student("fn2", "email2@u.nus.edu", "user2", "password2"));
		ss.createStudent(new Student("fn3", "email3@u.nus.edu", "user3", "password3"));
		ss.createStudent(new Student("fn4", "email4@u.nus.edu", "user4", "password4"));
		ss.createStudent(new Student("fn5", "email5@u.nus.edu", "user5", "password5"));
		}

		if(!bs.tableExist()) {
		bs.createBooking(new Booking("B1", dateTimeInput.dateInput("01/08/2022"), LocalTime.now(), 1, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B2", dateTimeInput.dateInput("02/08/2022"), LocalTime.now(), 2, BookingStatus.CANCELLED));
		bs.createBooking(new Booking("B3", dateTimeInput.dateInput("03/08/2022"), LocalTime.now(), 3, BookingStatus.REJECTED));
		bs.createBooking(new Booking("B4", dateTimeInput.dateInput("01/09/2022"), LocalTime.now(), 1, BookingStatus.WAITINGLIST));
		bs.createBooking(new Booking("B5", dateTimeInput.dateInput("01/01/2023"), LocalTime.now(), 2, BookingStatus.WAITINGLIST));
		}
		
		if(!rs.tableExist()) {
		rs.createReport(new Report("Food packages thrown everywhere!",Category.CLEANLINESS, StudentStatus.NORMAL));
		rs.createReport(new Report("Graffiti on walls!",Category.VANDALISE, StudentStatus.NORMAL));
		rs.createReport(new Report("The group inside doesn't want to leave!",Category.HOGGING, StudentStatus.NORMAL));
		rs.createReport(new Report("The tables and chairs are broken",Category.MISUSE, StudentStatus.NORMAL));
		rs.createReport(new Report("IS THAT A COCKROACH?!",Category.CLEANLINESS, StudentStatus.NORMAL));
		}
		
		if(!rms.tableExist()) {
		rms.createRoom(new Room(true ,"Projector and computer","Beacon",5));
		rms.createRoom(new Room(true ,"Computer","Frontier",8));
		rms.createRoom(new Room(true ,"Whiteboard","Jupiter",3));
		rms.createRoom(new Room(true ,"Projector","Mercury",10));
		rms.createRoom(new Room(true ,"Whiteboard and computer","Venus",6));
		}	
		
		if(!us.tableExist()) {
		us.createAdmin(new User("admin", "admin@u.nus.edu", "admin", "password"));
		}
	}
	
	public void loadBookingData() {
		Student s1 = ss.findStudentById("S00001");
		
		Booking b1 = bs.findBookingById("B00006");
		Booking b2 = bs.findBookingById("B00007");
		
		bs.addStudent(b2, s1);
		bs.addStudent(b1, s1);		
	}
}
	

