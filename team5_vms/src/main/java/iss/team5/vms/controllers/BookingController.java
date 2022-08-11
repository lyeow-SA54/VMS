package iss.team5.vms.controllers;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.helper.ResponsePojo;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Facility;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.model.User;
import iss.team5.vms.repositories.StudentRepo;
import iss.team5.vms.services.BookingService;
import iss.team5.vms.services.FacilityService;
import iss.team5.vms.services.RoomService;
import iss.team5.vms.services.StudentService;
import iss.team5.vms.services.UserService;
import iss.team5.vms.services.UserSessionService;

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
	
	@Autowired
	StudentRepo srepo;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	private UserSessionService userSessionService;
	
	@RequestMapping("/home")
	public ModelAndView studentHome(HttpServletRequest request) {
		User user = userSessionService.findUserBySession();
		if(!user.getRole().equals("STUDENT")) {
			ModelAndView mav = new ModelAndView("unauthorized-admin");
			return mav;
		}
		List<Room> rooms = rs.findAllRooms();
		Booking bookingForTheDay = new Booking();
		bookingForTheDay.setDate(LocalDate.now());
		bookingForTheDay.setTime(LocalTime.now());
		List<Booking> availableBookings = bs.checkBookingAvailable(bookingForTheDay, rooms);
		
		//String username = SecurityContextHolder.getContext().getAuthentication().getName();
		//Student s1 = ss.findStudentByUser(us.findUserByUsername(username));
		
		HttpSession session = request.getSession();
		Student student = (Student)session.getAttribute("student");	
		List<Booking> sBooking = bs.findBookingsByStudent(student); 
		
		List<Booking> findTodayBooking=sBooking.stream()
		.filter(b-> b.getDate()==LocalDate.now() && b.getStatus().toString().equalsIgnoreCase("SUCCESSFUL") )
		.collect(Collectors.toList());
		
		if (findTodayBooking.size() == 1) { 
			Booking bookingOfTheDay = findTodayBooking.get(0);
			ModelAndView mav = new ModelAndView("student-home-page");
			mav.addObject("bookingOfTheDay",bookingOfTheDay);
			mav.addObject("bookings",availableBookings);
			
			return mav;
		} else {
		
		
		ModelAndView mav = new ModelAndView("student-home-page");
		mav.addObject("bookings",availableBookings);
		return mav;}	
		
	}
	

	@RequestMapping("/checkin/{bookingId}")
	public ModelAndView bookingCheckin(@PathVariable("bookingId") String bookingId, HttpServletRequest request) {
		User user = userSessionService.findUserBySession();
		if(!user.getRole().equals("STUDENT")) {
			ModelAndView mav = new ModelAndView("unauthorized-admin");
			return mav;
		}
		// pending login implementation
		// hardcoded student object for now, final implementation should retrieve from
		// logged in context
		//String username = SecurityContextHolder.getContext().getAuthentication().getName();
		//Student student = ss.findStudentByUser(us.findUserByUsername(username));
		//Student student = ss.findStudentById("S00001");
		HttpSession session = request.getSession();
		Student student = (Student)session.getAttribute("student");	
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
		User user = userSessionService.findUserBySession();
		if(!user.getRole().equals("STUDENT")) {
			ModelAndView mav = new ModelAndView("unauthorized-admin");
			return mav;
		}
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
		User user = userSessionService.findUserBySession();
		if(!user.getRole().equals("STUDENT")) {
			ModelAndView mav = new ModelAndView("unauthorized-admin");
			return mav;
		}
		List<Room> rooms = rs.findRoomsByAttributes(room);
		List<Booking> bookings = bs.checkBookingAvailable(booking, rooms);
		ModelAndView mav = new ModelAndView("student-bookings-slot_selection");
		mav.addObject("bookings", bookings);
		mav.addObject("room", room);
		return mav;
	}
	
	@RequestMapping(value = "/booking/save", method = RequestMethod.POST)
	public String bookingNew(Booking booking, @RequestParam("roomid") String roomString, HttpServletRequest request) {
		
//		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
//		List <Student> sList = ss.findAllStudents(); 
//		List <Student> findStu = sList.stream()
//				.filter(s-> s.getUser().getUsername().equalsIgnoreCase(loggedInUser.getName()))
//				.collect(Collectors.toList());
//		
//		if (findStu.size() != 1) { 
//		    throw new IllegalStateException();
//		}
//		
//		Student student = findStu.get(0);
		Student student = (Student) session.getAttribute("student");
		Room room = rs.findRoomById(roomString);

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
		User user = userSessionService.findUserBySession();
		if(!user.getRole().equals("STUDENT")) {
			ModelAndView mav = new ModelAndView("unauthorized-admin");
			return mav;
		}
		ModelAndView mav = new ModelAndView("booking-success");
		Booking booking = bs.findBookingById(bookingId);
		mav.addObject("booking", booking);
		return mav;
	}


//	//this is for report form test
//	@RequestMapping("/reportform")
//	public String reportform(){
//		System.out.println("0 success");
//		return "misuse-report-form";
//	}
	
	@RequestMapping("/booking/history")
	public ModelAndView bookingHistory() {
		User user = userSessionService.findUserBySession();
		if(!user.getRole().equals("STUDENT")) {
			ModelAndView mav = new ModelAndView("unauthorized-admin");
			return mav;
		}

		Student student = (Student) session.getAttribute("student");

		List<Booking> bookings = bs.findBookingsByStudent(student);
		List<Booking> bookings2 = bs.checkBookingInProgress(bookings);
		ModelAndView mav = new ModelAndView("student-bookings-list");
		mav.addObject("bookings",bookings2);
		LocalDate datenow = LocalDate.now();
		LocalTime timenow = LocalTime.now();
		mav.addObject("datenow", datenow);
		mav.addObject("timenow", timenow);
		
		final String uri = "http://127.0.0.1:5000/predict?filename=be9349ed8f284a32ab286514be55d896.png";

		RestTemplate restTemplate = new RestTemplate();
		ResponsePojo response = restTemplate.getForObject(uri, ResponsePojo.class);
		String result = response.getResponse();
		System.out.println(result);
		   
		return mav;
	}
	
	@RequestMapping(value = "/booking/report/{bookingId}", method = RequestMethod.GET)
	public ModelAndView bookingReport(@PathVariable String bookingId) {
		User user = userSessionService.findUserBySession();
		if(!user.getRole().equals("STUDENT")) {
			ModelAndView mav = new ModelAndView("unauthorized-admin");
			return mav;
		}
		ModelAndView mav = new ModelAndView("misuse-report-form");
		Booking booking = bs.findBookingById(bookingId);
		mav.addObject("booking", booking);
		return mav;
	}
	
	@RequestMapping(value = "/booking/cancel/{bookingId}", method = RequestMethod.GET)
	public String cancelBooking(@PathVariable String bookingId) {
		Booking booking = bs.findBookingById(bookingId);
		booking.setStatus(BookingStatus.CANCELLED);
		bs.createBooking(booking);
		Room room = booking.getRoom();
		room.setBlockDuration(0);
		room.setBlockedStartTime(null);
		return "forward:/student/booking/history";
	}
	
	@RequestMapping(value = "/booking/extend/{bookingId}", method = RequestMethod.GET)
	public ModelAndView extendBooking(@PathVariable String bookingId) {
		User user = userSessionService.findUserBySession();
		if(!user.getRole().equals("STUDENT")) {
			ModelAndView mav = new ModelAndView("unauthorized-admin");
			return mav;
		}
		ModelAndView mav = new ModelAndView("booking-success");
		Booking booking = bs.findBookingById(bookingId);
		String outcomeMsg = "";
		Booking extendBooking = new Booking("placeholder", 
				booking.getDate(), 
				booking.getTime().plusHours(booking.getDuration()), 
				1, booking.getRoom());
		if (!bs.checkBookingByDateTimeRoom(extendBooking,booking.getRoom())) {
			outcomeMsg = "Booking extension request denied";
		}
		else {
			outcomeMsg = "Booking extension request approved";
			booking.setDuration(booking.getDuration()+1);
			bs.createBooking(booking);
		}
		mav.addObject("booking", booking);
		mav.addObject("outcomeMsg", outcomeMsg);
		return mav;
	}

}
