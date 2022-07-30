package iss.team5.vms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.services.BookingService;
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

}
