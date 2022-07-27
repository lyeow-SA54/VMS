package iss.team5.vms.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import iss.team5.vms.helper.BookingAvailablity;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.repositories.RoomRepo;
import iss.team5.vms.services.BookingService;
import iss.team5.vms.services.StudentService;


@Controller
@RequestMapping(value="/book")
public class StudentBookingController {
	
	@Autowired
	private BookingService bService;
	@Autowired
	private StudentService sService;
	
	@Autowired
	private RoomRepo rmrepo;
	
	@RequestMapping(value="/{sid}/new", method = RequestMethod.GET)
	public ModelAndView newBooking(@PathVariable String sid)
	{
		Student student = sService.findStudent(sid);
		ModelAndView mav = new ModelAndView("booking-form","booking",new Booking());
		ArrayList<Room> rmList = rmrepo.findAllByRoomAvailability(BookingAvailablity.OPEN);
		mav.addObject("student",student);
		mav.addObject("rmList",rmList);
		mav.addObject("booking",new Booking());
		return mav;
	}
	
	@RequestMapping(value="/{sid}/new", method = RequestMethod.POST)
	public ModelAndView createNewBooking(@ModelAttribute @Valid Booking booking, BindingResult result,
			@PathVariable String sid)		
	{
		if(result.hasErrors())
			return new ModelAndView("booking-form");
		
		Student student = sService.findStudent(sid);
		ModelAndView mav = new ModelAndView();
		bService.createBooking(new Booking());
		mav.addObject("booking",new Booking());
		mav.setViewName("forward:/book/{sid}/status");
		return mav;
	}
	
	@RequestMapping(value="/{sid}/{bid}/status", method = RequestMethod.GET)
	public ModelAndView bookingStatus(@PathVariable(name="sid") String sid, 
			@PathVariable(name="bid") String bid)
	{
		ModelAndView mav = new ModelAndView("booking-success");
		Student student = sService.findStudent(sid);
		Booking booking = bService.findBooking(bid);
		
		mav.addObject("booking",booking);
		return mav;
	}
	

}
