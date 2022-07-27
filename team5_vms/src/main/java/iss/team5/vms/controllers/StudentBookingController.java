package iss.team5.vms.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import iss.team5.vms.model.Booking;
import iss.team5.vms.services.BookingService;
import iss.team5.vms.services.StudentService;
;

@Controller
@RequestMapping("/book")
public class StudentBookingController {
	
	@Autowired
	private BookingService bService;
	@Autowired
	private StudentService sService;
	
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)	
	public ModelAndView newBooking() {
		ModelAndView mav = new ModelAndView("new_booking", "booking", new Booking());
		Student student = 
		ArrayList<Booking> bList = bService.findAllBookings();
		mav.addObject(bList);
		return mav;
		
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)	
	public ModelAndView createNewBooking(@ModelAttribute @Valid Booking booking,
			BindingResult result) {
		if(result.hasErrors())
			return new ModelAndView("new_booking");
		
		ModelAndView mav = new ModelAndView();
		String message = "New booking " + booking.getId() + " was successfully created.";
		System.out.println(message);
		bService.createBooking(booking);
		mav.setViewName("forward:/book");
		return mav;
		
	}
		
}
