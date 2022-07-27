package iss.team5.vms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import iss.team5.vms.model.Booking;
import iss.team5.vms.services.BookingService;
;

@Controller
@RequestMapping("/book")
public class StudentBookingController {
	
	@Autowired
	private BookingService bService;
	
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)	
	public ModelAndView newBooking() {
		ModelAndView mav = new ModelAndView("new_booking", "booking", new Booking());
		Student 
		
	}
		
}
