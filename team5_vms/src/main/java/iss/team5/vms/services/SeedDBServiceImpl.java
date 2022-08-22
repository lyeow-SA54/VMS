package iss.team5.vms.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import iss.team5.vms.generators.HashStringGenerator;
import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.helper.ReportCategory;
import iss.team5.vms.helper.ReportStatus;
import iss.team5.vms.helper.StudentStatus;
import iss.team5.vms.helper.dateTimeInput;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Facility;
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
	
	@Autowired
	FacilityService fs;
	
	public void databaseInit() {
		createInitialData();
		loadBookingData();
	}

	public void createInitialData() {
		if(!ss.tableExist()){
			User user = new User();
			user.setEmail("admin@u.nus.edu");
			user.setFirstName("admin");
			user.setLastName("");
			user.setRole("ADMIN");
			user.setGroupName("admin");
			user.setPassword(HashStringGenerator.getHash("admin", "admin"));
			us.createUser(user);
			
			ss.createStudent(new Student("Trevor", "Green", "email1@u.nus.edu", "Group1", 5));
			ss.createStudent(new Student("Tori", "Howe", "email2@u.nus.edu", "Group2", 6));
			ss.createStudent(new Student("Lula", "Thorpe", "email3@u.nus.edu", "Group3", 7));
			ss.createStudent(new Student("Manal", "White", "email4@u.nus.edu", "Group4", 8));
			ss.createStudent(new Student("Vanesa", "Reese", "email5@u.nus.edu", "Group5", 9));
		}

		if(!bs.tableExist()) {
		bs.createBooking(new Booking("B1", dateTimeInput.dateInput("08/12/2022"), LocalTime.now().minusMinutes(60), 60, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B2", dateTimeInput.dateInput("08/13/2022"), LocalTime.now(), 60, BookingStatus.CANCELLED));
		bs.createBooking(new Booking("B3", dateTimeInput.dateInput("08/14/2022"), LocalTime.now(), 60, BookingStatus.REJECTED));
		bs.createBooking(new Booking("B4", dateTimeInput.dateInput("08/15/2022"), LocalTime.now(), 60, BookingStatus.WAITINGLIST));
		bs.createBooking(new Booking("B5", LocalDate.now().minusDays(1), LocalTime.now(), 60, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B6", LocalDate.now().minusDays(1), LocalTime.now().minusMinutes(90), 60, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B7", LocalDate.now(), LocalTime.now().minusMinutes(120), 45, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B8", LocalDate.now(), LocalTime.now().minusMinutes(45), 90, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B9", LocalDate.now().plusDays(1), dateTimeInput.timeInput("14:00:05"), 120, BookingStatus.SUCCESSFUL));


		/*bs.createBooking(new Booking("B10", LocalDate.now().minusDays(24), LocalTime.now().minusMinutes(60), 60, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B11", LocalDate.now().minusDays(22), LocalTime.now().minusMinutes(15), 45, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B12", LocalDate.now().minusDays(21), LocalTime.now().minusMinutes(45), 90, BookingStatus.REJECTED));
		bs.createBooking(new Booking("B13", LocalDate.now().minusDays(18), LocalTime.now().minusMinutes(45),75, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B14", LocalDate.now().minusDays(16), LocalTime.now().minusMinutes(75), 60, BookingStatus.CANCELLED));
		bs.createBooking(new Booking("B15", LocalDate.now().minusDays(15), LocalTime.now().plusMinutes(60), 45, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B16", LocalDate.now().minusDays(11), LocalTime.now().minusMinutes(45), 90, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B17", LocalDate.now().minusDays(10), LocalTime.now().plusMinutes(45), 30, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B18", LocalDate.now().minusDays(8), LocalTime.now().minusMinutes(90), 60, BookingStatus.CANCELLED));
		bs.createBooking(new Booking("B19", LocalDate.now().minusDays(4), LocalTime.now().minusMinutes(30), 45, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B20", LocalDate.now().minusDays(2), LocalTime.now().minusMinutes(45), 90, BookingStatus.SUCCESSFUL));
		bs.createBooking(new Booking("B21", LocalDate.now().minusDays(1), LocalTime.now().minusMinutes(45), 75, BookingStatus.CANCELLED));*/
		}
		
		if(!rs.tableExist()) {
		rs.createReport(new Report("Food packages thrown everywhere!","test.png",ReportCategory.CLEANLINESS, ReportStatus.PROCESSING));
		rs.createReport(new Report("Graffiti on walls!","test.png",ReportCategory.VANDALISE, ReportStatus.PROCESSING));
		rs.createReport(new Report("The group inside doesn't want to leave!","test.png",ReportCategory.HOGGING, ReportStatus.PROCESSING));
		rs.createReport(new Report("The tables and chairs are broken","test.png",ReportCategory.MISUSE, ReportStatus.PROCESSING));
		rs.createReport(new Report("IS THAT A COCKROACH?!","test.png",ReportCategory.CLEANLINESS, ReportStatus.PROCESSING));
		}
	
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
		Student s1 = ss.findStudentById("S1001");
		Student s2 = ss.findStudentById("S1002");

		Booking b1 = bs.findBookingById("B1001");
		Booking b2 = bs.findBookingById("B1002");
		Booking b3 = bs.findBookingById("B1003");
		Booking b4 = bs.findBookingById("B1004");
		Booking b5 = bs.findBookingById("B1005");
		Booking b6 = bs.findBookingById("B1006");

		Report rp1 = rs.findReportById("RE1001");
		Report rp2 = rs.findReportById("RE1002");
		Report rp3 = rs.findReportById("RE1003");
		Report rp4 = rs.findReportById("RE1004");
		Report rp5 = rs.findReportById("RE1005");

		
		Room r1 = rms.findRoomById("RM1001");
		Room r2 = rms.findRoomById("RM1002");
		Room r3 = rms.findRoomById("RM1003");
		
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
		bs.addStudent(b6, s1);

		rs.addBooking(rp1,b1);
		rs.addBooking(rp2,b2);
		rs.addBooking(rp3,b3);
		rs.addBooking(rp4,b4);
		rs.addBooking(rp5,b5);
		
		bs.addRoom(b1, r1);
		bs.addRoom(b2, r2);
		bs.addRoom(b3, r3);
		bs.addRoom(b4, r1);
		bs.addRoom(b5, r3);
		bs.addRoom(b6, r3);

		Student s3 = ss.findStudentById("S1003");
		Booking b7 = bs.findBookingById("B1007");
		Booking b8 = bs.findBookingById("B1008");
		Booking b9 = bs.findBookingById("B1009");
		bs.addStudent(b7,s1);
		bs.addStudent(b8,s1);
		bs.addStudent(b9,s1);
		bs.addRoom(b7,r1);
		bs.addRoom(b8,r1);
		bs.addRoom(b9,r1);
		s3.setScore(5);
		ss.changeStudent(s3);
	}
}