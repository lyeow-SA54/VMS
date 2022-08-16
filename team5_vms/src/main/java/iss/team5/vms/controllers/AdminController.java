package iss.team5.vms.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import iss.team5.vms.email.service.EmailService;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Facility;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.model.User;
import iss.team5.vms.services.BookingService;
import iss.team5.vms.services.FacilityService;
import iss.team5.vms.services.RoomService;
import iss.team5.vms.services.StudentService;
import iss.team5.vms.services.UserSessionService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private RoomService rService;

	@Autowired
	private FacilityService fService;

	@Autowired
	private StudentService sService;

	@Autowired
	private BookingService bService;

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private EmailService eService;

	@RequestMapping(value = "/rooms/create", method = RequestMethod.GET)
	public ModelAndView newRoom() {
		User user = userSessionService.findUserBySession();
		if (!user.getRole().equals("ADMIN")) {
			ModelAndView mav = new ModelAndView("unauthorized-student");
			return mav;
		}
		ModelAndView mav = new ModelAndView("room-form");
		mav.addObject("room", new Room());
		List<Facility> facilities = (List<Facility>) fService.findAllFacilities();
		mav.addObject("fList", facilities);
		return mav;
	}

	@RequestMapping(value = "/rooms/create", method = RequestMethod.POST)
	public ModelAndView createRoom(@ModelAttribute @Valid Room room, BindingResult result, @RequestParam("roomName") String roomName) {
		User user = userSessionService.findUserBySession();
		List<Room> rooms = rService.findAllRooms();
		HashSet<String> names = new HashSet<>();
		for (Room r : rooms) {
			names.add(r.getRoomName());
		}
		if (!user.getRole().equals("ADMIN")) {
			ModelAndView mav = new ModelAndView("unauthorized-student");
			return mav;
		}
		if (result.hasErrors())
			return new ModelAndView("room-form");
		for(String name : names) {
			if(name.equalsIgnoreCase(roomName)) {
				ModelAndView mav = new ModelAndView("room-form");
				mav.addObject("taken", true);
				return mav;
			}
		}
		ModelAndView mav = new ModelAndView("forward:/admin/rooms/list");
		room.setRoomName(roomName);
		rService.createRoom(room);
		return mav;
	}

	@RequestMapping(value = "/rooms/search", method = RequestMethod.GET)
	public ModelAndView searchRoom(@RequestParam("searchRoom") String roomName, @RequestParam("facility") String facStr,
			@RequestParam("availability") String ava, Model model) {
		User user = userSessionService.findUserBySession();
		if (!user.getRole().equals("ADMIN")) {
			ModelAndView mav = new ModelAndView("unauthorized-student");
			return mav;
		}
		ModelAndView mav = new ModelAndView("rooms");

		List<Booking> bookings = bService.findAllBookings();
		HashSet<String> roomIds = new HashSet<>();
		for (Booking b : bookings) {
			roomIds.add(b.getRoom().getId());
		}
		List<Room> rooms = rService.findAllRooms();
		mav.addObject("rooms", rooms);
		mav.addObject("roomids", roomIds);

		List<Facility> facilities = (List<Facility>) fService.findAllFacilities();
		mav.addObject("checkBoxFacilities", facilities);
		mav.addObject("searchStr", roomName);
		System.out.println("Facilities" + facStr);
		if (roomName != "") {
			mav.addObject("rooms",
					rService.searchRoomByNameFacilityAvailability(roomName, facStr, Boolean.parseBoolean(ava)));
		} else {
			mav.addObject("rooms", rService.searchRoomByFacilityAvailability(facStr, Boolean.parseBoolean(ava)));
		}
		return mav;
	}

	@RequestMapping(value = "/rooms/list")
	@ResponseBody
	public ModelAndView roomList() {
		User user = userSessionService.findUserBySession();
		List<Booking> bookings = bService.findAllBookings();
		HashSet<String> roomIds = new HashSet<>();
		for (Booking b : bookings) {
			roomIds.add(b.getRoom().getId());
		}
		if (user != null) {
			if (!user.getRole().equals("ADMIN")) {
				ModelAndView mav = new ModelAndView("unauthorized-student");
				return mav;
			}
		}
		ModelAndView mav = new ModelAndView("rooms");
		List<Room> rooms = rService.findAllRooms();
		mav.addObject("rooms", rooms);
		List<Facility> facilities = (List<Facility>) fService.findAllFacilities();
		mav.addObject("checkBoxFacilities", facilities);
		mav.addObject("roomids", roomIds);
		return mav;
	}

	@RequestMapping(value = "/rooms/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editRoom(@PathVariable String id) {
		User user = userSessionService.findUserBySession();
		if (!user.getRole().equals("ADMIN")) {
			ModelAndView mav = new ModelAndView("unauthorized-student");
			return mav;
		}
		ModelAndView mav = new ModelAndView("room-edit");
		Room room = rService.findRoomById(id);
		mav.addObject("room", room);
		List<Facility> facilities = (List<Facility>) fService.findAllFacilities();
		mav.addObject("fList", facilities);
		List<Boolean> availabilities = new ArrayList<>();
		availabilities.add(Boolean.TRUE);
		availabilities.add(Boolean.FALSE);
		mav.addObject("availabilities", availabilities);
		return mav;
	}

	@RequestMapping(value = "/rooms/edit", method = RequestMethod.POST)
	public ModelAndView editRoom(@ModelAttribute @Valid Room room, BindingResult result) {
		User user = userSessionService.findUserBySession();
		if (!user.getRole().equals("ADMIN")) {
			ModelAndView mav = new ModelAndView("unauthorized-student");
			return mav;
		}
		if (result.hasErrors())
			return new ModelAndView("room-edit");
		ModelAndView mav = new ModelAndView("rooms");
		rService.changeRoom(room);
		List<Room> rooms = rService.findAllRooms();
		List<Facility> facilities = (List<Facility>) fService.findAllFacilities();
		mav.addObject("checkBoxFacilities", facilities);
		mav.addObject("rooms", rooms);
		return mav;
	}

	@RequestMapping(value = "/rooms/delete/{id}")
	public ModelAndView deleteRoom(@PathVariable("id") String id) {
		User user = userSessionService.findUserBySession();
		if (!user.getRole().equals("ADMIN")) {
			ModelAndView mav = new ModelAndView("unauthorized-student");
			return mav;
		}
		ModelAndView mav = new ModelAndView("forward:/admin/rooms/list");
		Room room = rService.findRoomById(id);
		rService.removeRoom(room);
		return mav;
	}

	@RequestMapping(value = "/students/create", method = RequestMethod.GET)
	public ModelAndView newStudent() {
		User user = userSessionService.findUserBySession();
		if (!user.getRole().equals("ADMIN")) {
			ModelAndView mav = new ModelAndView("unauthorized-student");
			return mav;
		}
		ModelAndView mav = new ModelAndView("student-form");
		mav.addObject("student", new Student());
		return mav;
	}

	@RequestMapping(value = "/students/create", method = RequestMethod.POST)
	public ModelAndView createStudent(@ModelAttribute @Valid Student student, BindingResult result, @RequestParam("groupName") String groupName, @RequestParam("email") String email) {
		User user = userSessionService.findUserBySession();
		List<Student> students = sService.findAllStudents();
		HashSet<String> names = new HashSet<>();
		HashSet<String> emails = new HashSet<>();
		for (Student stu : students) {
			names.add(stu.getUser().getGroupName());
		}
		for (Student stu : students) {
			emails.add(stu.getUser().getEmail());
		}
		if (!user.getRole().equals("ADMIN")) {
			ModelAndView mav = new ModelAndView("unauthorized-student");
			return mav;
		}
		if (result.hasErrors())
			return new ModelAndView("student-form");
		for(String name : names) {
			if(name.equalsIgnoreCase(groupName)) {
				ModelAndView mav = new ModelAndView("student-form");
				mav.addObject("duplicate", true);
				return mav;
			}
		}
		for(String e : emails) {
			if(e.equals(email)) {
				ModelAndView mav = new ModelAndView("student-form");
				mav.addObject("email", true);
				return mav;
			}
		}
		ModelAndView mav = new ModelAndView("forward:/admin/students/list");
		student.getUser().setGroupName(groupName);
		student.getUser().setEmail(email);
		sService.createStudent(student);
		User stu = student.getUser();
		try {
			eService.sendMail(stu);
			System.out.println("Success");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mav;
	}

	@RequestMapping(value = "/students/list")
	@ResponseBody
	public ModelAndView studentList() {
		User user = userSessionService.findUserBySession();
		List<Booking> bookings = bService.findAllBookings();
		HashSet<String> studentIds = new HashSet<>();
		for (Booking b : bookings) {
			studentIds.add(b.getStudent().getId());
		}
		if (user != null) {
			if (!user.getRole().equals("ADMIN")) {
				ModelAndView mav = new ModelAndView("unauthorized-student");
				return mav;
			}
		}
		ModelAndView mav = new ModelAndView("students");
		List<Student> students = sService.findAllStudents();
		mav.addObject("students", students);
		mav.addObject("studentids", studentIds);
		return mav;
	}

	@RequestMapping(value = "/students/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editProfile(@PathVariable String id) {
		User user = userSessionService.findUserBySession();
		Student stu = sService.findStudentById(id);
		if (!user.getRole().equals("ADMIN")) {
			ModelAndView mav = new ModelAndView("unauthorized-student");
			return mav;
		}
		ModelAndView mav = new ModelAndView("student-profile-edit-for-admin");
		mav.addObject("student", stu);
		return mav;
	}

	@RequestMapping(value = "/students/edit", method = RequestMethod.POST)
	public ModelAndView saveProfile(@ModelAttribute @Valid Student student, BindingResult result) {
		User user = userSessionService.findUserBySession();
		if (!user.getRole().equals("ADMIN")) {
			ModelAndView mav = new ModelAndView("unauthorized-student");
			return mav;
		}
		if (result.hasErrors())
			return new ModelAndView("student-profile-edit-for-admin");
		sService.editStudent(student);
		ModelAndView mav = new ModelAndView("students");
		List<Student> students = sService.findAllStudents();
		mav.addObject("students", students);
		return mav;
	}

	@RequestMapping(value = "/students/delete/{id}")
	public ModelAndView deleteStudent(@PathVariable("id") String id) {
		User user = userSessionService.findUserBySession();
		if (!user.getRole().equals("ADMIN")) {
			ModelAndView mav = new ModelAndView("unauthorized-student");
			return mav;
		}
		ModelAndView mav = new ModelAndView("forward:/admin/students/list");
		Student student = sService.findStudentById(id);
		sService.removeStudent(student);
		return mav;
	}
}