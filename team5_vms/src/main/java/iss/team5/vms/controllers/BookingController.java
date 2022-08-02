package iss.team5.vms.controllers;


import java.time.LocalTime;
import java.util.ArrayList;

import java.util.List;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.helper.dateTimeInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Facility;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.services.BookingService;
import iss.team5.vms.services.FacilityService;
import iss.team5.vms.services.RoomService;
import iss.team5.vms.services.StudentService;

@Controller
@RequestMapping("/student")
public class BookingController {

	@Autowired
	BookingService bs;

	@Autowired
	StudentService ss;
	
	@Autowired
	RoomService rs;
	
	@Autowired
	FacilityService fs;

	@RequestMapping("/checkin/{bookingId}")
	public ModelAndView bookingCheckin(@PathVariable("bookingId") String bookingId) {

		// pending login implementation
		// hardcoded student object for now, final implementation should retrieve from
		// logged in context
		Student student = ss.findStudentById("S00001");
		// pending proper url to be forwarded to on check-in completion
		ModelAndView mav = new ModelAndView("student-bookings-list");
		Booking booking = bs.findBookingById(bookingId);
		String outcomeMsg = "";
		if (booking == null) {
			outcomeMsg = "Error: booking not found";
		} else {
			outcomeMsg = bs.checkIn(student, booking);
		}
		mav.addObject("outcomeMsg", outcomeMsg);
		return mav;
	}
	
	@RequestMapping(value = "/booking/options", method = RequestMethod.GET)
	public ModelAndView bookingOptionSelection() {

		Booking booking = new Booking();
		Room room = new Room();
		ModelAndView mav = new ModelAndView("student-bookings-filter_selection");
		List<Facility> facilities = fs.findAllFacilities();
		mav.addObject("fList", facilities);
		mav.addObject("booking", booking);
		mav.addObject("room", room);
		return mav;
	}
	
	@RequestMapping(value = "/booking/options", method = RequestMethod.POST)
	public ModelAndView bookingOptionSelected(Booking booking, Room room) {

		List<Room> rooms = rs.findRoomsByAttributes(room);
		List<Booking> bookings = bs.checkBookingAvailable(booking, rooms);
		ModelAndView mav = new ModelAndView("student-bookings-slot_selection");
		mav.addObject("bookings", bookings);
		return mav;
	}


	//this is for report form test
	@RequestMapping("/reportform")
	public String reportform(){
		System.out.println("0 success");
		return "misuse-report-form";
	}
	
	@RequestMapping("/booking/history")//using pathvariable to get student
	public ModelAndView bookingHistory(Student student) {
		/*List<Booking> bookings = bs.findBookingsByStudent(student);*/
		//no login now, so can not findBookingsByStudent, use a new null list to replacement;
		//once we have login role for this part, add additional if logical part for handle null history
		/*List<Booking> bookings = new ArrayList<>();*/
		//hardcoding new student and booking for test;
		Student s1 = ss.findStudentById("S00002");
//		bs.createBooking(new Booking("B6", dateTimeInput.dateInput("08/06/2023"), LocalTime.now(),4, BookingStatus.WAITINGLIST,s1));
		List<Booking> bookings = bs.findBookingsByStudent(s1);
		ModelAndView mav = new ModelAndView("student-bookings-list");
		mav.addObject("bookings",bookings);
		return mav;
	}

}
