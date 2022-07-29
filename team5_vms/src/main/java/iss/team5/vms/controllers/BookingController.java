package iss.team5.vms.controllers;

import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import iss.team5.vms.helper.QRGenerator;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Student;
import iss.team5.vms.services.BookingService;
import iss.team5.vms.services.StudentService;

@Controller
@RequestMapping("/bookings")
public class BookingController {

	@Autowired
	BookingService bs;

	@Autowired
	StudentService ss;

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
	
	

}
