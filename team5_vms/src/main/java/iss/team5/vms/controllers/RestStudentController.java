package iss.team5.vms.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.repositories.StudentRepo;
import iss.team5.vms.services.BookingService;
import iss.team5.vms.services.FacilityService;
import iss.team5.vms.services.RoomService;
import iss.team5.vms.services.StudentService;
import iss.team5.vms.services.UserService;

@RestController
@RequestMapping(path = "api/student", produces = "application/json")
public class RestStudentController {

	@Autowired
	BookingService bs;

	@Autowired
	StudentService ss;

	@Autowired
	RoomService rs;

	@Autowired
	FacilityService fs;

	@Autowired
	UserService us;

	@Autowired
	StudentRepo srepo;

	@RequestMapping("/booking/history")
	public List<Booking> bookingHistory(Student student) {
		Student s1 = ss.findStudentById(3);
		List<Booking> bookings = bs.findBookingsByStudent(s1);
		return bs.checkBookingInProgress(bookings);
	}

	@PostMapping(value = "/booking/options")
	public List<Booking> bookingOptions(@RequestBody Map<String, Object> payload) {
		LocalDate date = LocalDate.parse((String) payload.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalTime time = LocalTime.parse((String) payload.get("time"));

		Booking booking = new Booking("1", date, time, 2, BookingStatus.WAITINGLIST);
		Room room = new Room("1", 2, fs.jsonToFacilityList((String) payload.get("facilities")));

		List<Room> rooms = rs.findRoomsByAttributes(room);
		List<Booking> bookings = bs.checkBookingAvailable(booking, rooms);
//		
//		System.out.println(payload.get("facilities"));
//		System.out.println(payload.get("date"));
//		System.out.println(payload.get("time"));
//		System.out.println(payload.get("capacity"));
		
		return bookings;
	}

}
