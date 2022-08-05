package iss.team5.vms.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import iss.team5.vms.model.Booking;
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
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		Student s1 = ss.findStudentByUser(us.findUserByUsername(username));
		Student s1 = ss.findStudentById(3);
		List<Booking> bookings = bs.findBookingsByStudent(s1);
		return bs.checkBookingInProgress(bookings);
//		ModelAndView mav = new ModelAndView("student-bookings-list");
//		mav.addObject("bookings",bookings2);
//		LocalDate datenow = LocalDate.now();
//		LocalTime timenow = LocalTime.now();
//		mav.addObject("datenow", datenow);
//		mav.addObject("timenow", timenow);
//		return mav;
	}
	
}
