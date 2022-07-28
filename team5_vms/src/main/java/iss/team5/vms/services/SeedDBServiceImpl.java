package iss.team5.vms.services;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.team5.vms.helper.dateTimeInput;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Student;

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
		}

		if(!bs.tableExist()) {
		bs.createBooking(new Booking("B1", dateTimeInput.dateInput("01/01/2022"), LocalTime.now(), 1));
		bs.createBooking(new Booking("B2", dateTimeInput.dateInput("01/01/2022"), LocalTime.now(), 1));
		}
	}
	
	public void loadBookingData() {
		Student s1 = ss.findStudentById("S00001");
		
		Booking b1 = bs.findBookingById("B00002");
		Booking b2 = bs.findBookingById("B00003");
		
		bs.addStudent(b2, s1);
		bs.addStudent(b1, s1);		
	}
	
}
