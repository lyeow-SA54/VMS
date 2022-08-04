package iss.team5.vms.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.helper.Category;
import iss.team5.vms.helper.StudentStatus;
import iss.team5.vms.helper.dateTimeInput;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Facility;
import iss.team5.vms.model.Report;
import iss.team5.vms.model.Role;
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
	
	@Autowired
	FacilityService fs;
	
	public void databaseInit() {
		createInitialData();
		loadBookingData();
	}

	public void createInitialData() {
		if(!ss.tableExist()){
			ss.createStudent(new Student("fn1", "ln1", "email1@u.nus.edu", "user1"));
			ss.createStudent(new Student("fn2", "ln2", "email2@u.nus.edu", "user2"));
			ss.createStudent(new Student("fn3", "ln3", "email3@u.nus.edu", "user3"));
			ss.createStudent(new Student("fn4", "ln4", "email4@u.nus.edu", "user4"));
			ss.createStudent(new Student("fn5", "ln5", "email5@u.nus.edu", "user5"));
		}

		if(!bs.tableExist()) {
		bs.createBooking(new Booking("B1", dateTimeInput.dateInput("08/01/2022"), LocalTime.now().minusHours(1), 1, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B2", dateTimeInput.dateInput("08/02/2022"), LocalTime.now(), 2, BookingStatus.CANCELLED));
		bs.createBooking(new Booking("B3", dateTimeInput.dateInput("08/03/2022"), LocalTime.now(), 3, BookingStatus.REJECTED));
		bs.createBooking(new Booking("B4", dateTimeInput.dateInput("08/04/2022"), LocalTime.now(), 1, BookingStatus.WAITINGLIST));
		bs.createBooking(new Booking("B5", LocalDate.now(), LocalTime.now(), 2, BookingStatus.WAITINGLIST));
		}
		
		if(!rs.tableExist()) {
		rs.createReport(new Report("Food packages thrown everywhere!",Category.CLEANLINESS, StudentStatus.NORMAL));
		rs.createReport(new Report("Graffiti on walls!",Category.VANDALISE, StudentStatus.NORMAL));
		rs.createReport(new Report("The group inside doesn't want to leave!",Category.HOGGING, StudentStatus.NORMAL));
		rs.createReport(new Report("The tables and chairs are broken",Category.MISUSE, StudentStatus.NORMAL));
		rs.createReport(new Report("IS THAT A COCKROACH?!",Category.CLEANLINESS, StudentStatus.NORMAL));
		}
			
		
//		if(!us.tableExist()) {
//			Role role = new Role("ADMIN");
//			List<Role> rolelist = List.of(role);
//			User user = new User();
//			user.setEmail("admin@u.nus.edu");
//			user.setFirstName("admin");
//			user.setLastName("");
//			user.setRoles(rolelist);
//			user.setUsername("admin");
//			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
//			us.createUser(user);
//		}
	
		
		if(!fs.tableExist()) {
			Facility f1 = new Facility("Projector");
			fs.createFacility(f1);
			Facility f2 = new Facility("White Board");
			fs.createFacility(f2);
			Facility f3 = new Facility("Computer");
			fs.createFacility(f3);
			}
		
		if(!rms.tableExist()) {
			rms.createRoom(new Room(true ,"Beacon",5));
			rms.createRoom(new Room(true ,"Frontier",8));
			rms.createRoom(new Room(true ,"Jupiter",3));
			rms.createRoom(new Room(true ,"Mercury",10));
			rms.createRoom(new Room(true ,"Venus",6));
			}
	}
	
	public void loadBookingData() {
		Student s1 = ss.findStudentById("S00001");
		Student s2 = ss.findStudentById("S00003");
		
		Booking b1 = bs.findBookingById("B00011");
		Booking b2 = bs.findBookingById("B00012");
		Booking b3 = bs.findBookingById("B00013");
		Booking b4 = bs.findBookingById("B00014");
		Booking b5 = bs.findBookingById("B00015");
		
		Room r1 = rms.findRoomById("RM00024");
		Room r2 = rms.findRoomById("RM00022");
		Room r3 = rms.findRoomById("RM00023");
		
		List<Facility> projecter = fs.findFacilityByName("Projector");
		List<Facility> wb = fs.findFacilityByName("White Board");
		List<Facility> pc = fs.findFacilityByName("Computer");
		
		List<Facility> fl1 = new ArrayList<Facility>();
		List<Facility> fl2 = new ArrayList<Facility>();
		List<Facility> fl3 = new ArrayList<Facility>();
		
		fl1.addAll(pc);
		fl1.addAll(wb);
		fl1.addAll(projecter);
		
		fl2.addAll(wb);
		fl2.addAll(projecter);
		
		fl3.addAll(projecter);
		
		r1.setFacilities(fl1);
		r2.setFacilities(fl2);
		r3.setFacilities(fl3);
		
		rms.createRoom(r3);
		rms.createRoom(r2);
		rms.createRoom(r1);
		
		bs.addStudent(b1, s1);
		bs.addStudent(b2, s1);	
		bs.addStudent(b3, s2);	
		bs.addStudent(b4, s2);	
		bs.addStudent(b5, s2);	
		
		bs.addRoom(b1, r1);
		bs.addRoom(b2, r2);
		bs.addRoom(b3, r3);
		bs.addRoom(b4, r1);
		bs.addRoom(b5, r3);
		
	}
}
	

