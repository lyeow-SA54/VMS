package iss.team5.vms.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Facility;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.services.BookingService;
import iss.team5.vms.services.FacilityService;
import iss.team5.vms.services.RoomService;
import iss.team5.vms.services.StudentService;
import iss.team5.vms.services.UserService;

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
	
	@Autowired
	UserService us;

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
		mav.addObject("room", room);
		return mav;
	}
	
	@RequestMapping(value = "/booking/save", method = RequestMethod.POST)
	public String bookingNew(Booking booking, @RequestParam("roomid") String roomString) {
		Room room = rs.findRoomById(roomString);
		Student student = ss.findStudentById("S00001");
		booking.setStudent(student);
		booking.setRoom(room);
//		booking.setRoom(rs.findRoomById(room.getId()));
//		booking.setRoom(rs.findRoomById(booking.getRoom().getId()));
//		List<Room> rooms = rs.findRoomsByAttributes(room);
//		List<Booking> bookings = bs.checkBookingAvailable(booking, rooms);
		if (!bs.checkBookingByDateTimeRoom(booking,room)) {
			booking.setStatus(BookingStatus.REJECTED);
			return "forward:/student/booking/status/"+booking.getId();
		}
		else
		{
//			booking.setRoom(rs.findRoomById(room.getId()));
			if (booking.getStudent().getScore() >= 3)
			{
			booking.setStatus(BookingStatus.WAITINGLIST);
			bs.scheduleWaitingList(booking, room);
			bs.createBooking(booking);
			return "forward:/student/booking/status/"+booking.getId();
			}
			else
			{
			booking.setStatus(BookingStatus.SUCCESSFUL);
			bs.createBooking(booking);
			return "forward:/student/booking/status/"+booking.getId();
			}
//			return "error";	
		}
	}

	@RequestMapping(value = "booking/status/{bookingId}")
	public ModelAndView bookingStatus(@PathVariable("bookingId") String bookingId) {
		ModelAndView mav = new ModelAndView("booking-success");
		Booking booking = bs.findBookingById(bookingId);
		mav.addObject("booking", booking);
		return mav;
	}


	//this is for report form test
	@RequestMapping("/reportform")
	public String reportform(){
		System.out.println("0 success");
		return "misuse-report-form";
	}
	
	@RequestMapping("/booking/history")
	public ModelAndView bookingHistory(Student student) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Student s1 = ss.findStudentByUser(us.findUserByUsername(username));
		List<Booking> bookings = bs.findBookingsByStudent(s1);
		ModelAndView mav = new ModelAndView("student-bookings-list");
		mav.addObject("bookings",bookings);
		return mav;
	}
	
	@RequestMapping(value = "/booking/cancel/{bookingId}", method = RequestMethod.GET)
	public String cancelBooking(@PathVariable String bookingId) {
		Booking booking = bs.findBookingById(bookingId);
		booking.setStatus(BookingStatus.CANCELLED);
		bs.createBooking(booking);
		return "forward:/student/booking/history";
	}

}
