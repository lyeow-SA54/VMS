package iss.team5.vms.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.team5.vms.DTO.Account;
import iss.team5.vms.generators.JWTGenerator;
import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.helper.Category;
import iss.team5.vms.helper.ReportStatus;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Report;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.model.User;
import iss.team5.vms.services.AccountAuthenticateService;
import iss.team5.vms.services.BookingService;
import iss.team5.vms.services.FacilityService;
import iss.team5.vms.services.ReportService;
import iss.team5.vms.services.RoomService;
import iss.team5.vms.services.StudentService;
import iss.team5.vms.services.UserService;

@RestController
@RequestMapping(path = "api/student", produces = "application/json")
public class Android_StudentController {

	@Autowired
	BookingService bs;

	@Autowired
	StudentService ss;

	@Autowired
	RoomService rms;

	@Autowired
	ReportService rs;

	@Autowired
	FacilityService fs;

	@Autowired
	UserService us;

	@Autowired
	private AccountAuthenticateService accAuthService;

	@PostMapping(value = "/login")
	public Map<String, Object> loginAndroid(@RequestBody Account account) {
			User user = accAuthService.authenticateAccount(account);
			Map<String, Object> mapResponse = new HashMap<String, Object>();
			if(user!=null) {
			String id = String.valueOf(user.getId());
			String accessToken = JWTGenerator.generateJWT(id, "jwtauthenticator", account.getUsername(), 604800000);
//			JWTGenerator.verifyJWT(accessToken);
			Student s = ss.findStudentByUser(user);
			mapResponse.put("studentId", s.getId());
			mapResponse.put("response", accessToken);
			return mapResponse;
			}
			mapResponse.put("response", "Invalid login");
			return mapResponse;
		}

//		Map<String, Object> response = new HashMap<String, Object>();
//		Map<Student, String> map = accAuthService.getMap();
//		User user = accAuthService.authenticateAccount(account);
//		if (user == null) {
//			System.out.println("invalid login");
//			response.put("response", "Invalid login");
//			return response;
//		} else {
//			Student s = ss.findStudentByUser(user);
//			String token = accAuthService.generateNewToken();
//			if (map.containsKey(ss.findStudentByUser(user))) {
//				map.replace(ss.findStudentByUser(user), token);
//			} else {
//				map.put(s, token);
//			}
//			response.put("response", token);
//			response.put("studentId", s.getId());
//			System.out.println(map.get(s));
//			return response;
//		}
//	}

//	@PostMapping(value = "/logout/{token}")
//	public Map<String, Object> loginAndroid(@PathVariable String token) {
//		Map<String, Object> response = new HashMap<String, Object>();
//		Map<Student, String> map = accAuthService.getMap();
//		Student s = map.entrySet().stream().filter(m -> m.getValue().equals(token)).map(Map.Entry::getKey).findFirst()
//				.get();
//		map.remove(s);
//		System.out.println(s);
//		response.put("response", HttpStatus.CONTINUE);
//		return response;
//	}

	@PostMapping("/booking/history/{token}")
	public List<Booking> bookingHistoryAndroid(@PathVariable String token,
			@RequestBody List<Map<String, Object>> rawPayload) {
//		String token = (String) payload.get("token");
//		System.out.println(token);
		if (JWTGenerator.verifyJWT(token)) {
			Map<String, Object> payload = rawPayload.get(0);
			Student s = ss.findStudentById(Integer.parseInt((String) payload.get("studentId")));
			System.out.println(payload.get("studentId"));
			List<Booking> bookings = bs.findBookingsByStudent(s);
			System.out.println("returning list");
			return bs.checkBookingInProgress(bookings);
		} else
			return null;
	}

	@PostMapping(value = "/booking/options/{token}")
	public List<Booking> bookingOptionsAndroid(@PathVariable String token, @RequestBody List<Map<String, Object>> rawPayload) {
		
		if (JWTGenerator.verifyJWT(token)) {
		Map<String, Object> payload = rawPayload.get(0);

		LocalDate date = LocalDate.parse((String) payload.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalTime time = LocalTime.parse((String) payload.get("time"));

		Booking booking = new Booking("1", date, time, Integer.parseInt((String) payload.get("duration")),
				BookingStatus.WAITINGLIST);
		Room room = new Room("1", Integer.parseInt((String) payload.get("capacity")),
				fs.jsonToFacilityList((String) payload.get("facilities")));

		List<Room> rooms = rms.findRoomsByAttributes(room);
		List<Booking> bookings = bs.checkBookingAvailable(booking, rooms);

//		System.out.println(payload.get("facilities"));
//		System.out.println(payload.get("date"));
//		System.out.println(payload.get("time"));
//		System.out.println(payload.get("capacity"));

		return bookings;}
		else return null;
	}

	@PostMapping(value = "/report/save/{token}")
	public String newReportAndroid(@PathVariable String token, @RequestBody List<Map<String, Object>> rawPayload) {

		if (JWTGenerator.verifyJWT(token)) {
		Map<String, Object> payload = rawPayload.get(0);

		String encodedString = (String) payload.get("image");
		byte[] encodedByte = Base64.decodeBase64(encodedString);

		String name = UUID.randomUUID().toString().replaceAll("-", "");
		String imageType = ".png";
		String filePath = "C:/VMS/img/";
		File outputFileFolder = new File(filePath);
		if (!outputFileFolder.exists()) {
			outputFileFolder.mkdirs();
		}
		try {
			FileUtils.writeByteArrayToFile(new File(filePath + name + imageType), encodedByte);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// add path to report
		Student student = ss.findStudentById(Integer.parseInt((String) payload.get("studentId")));
		Booking booking = bs.findStudentCurrentBooking(student);
		Booking lastBooking = bs.findLastBooking(booking);

		rs.createReport(new Report((String) payload.get("details"), name + imageType, lastBooking,
				ReportStatus.PROCESSING, Category.valueOf((String) payload.get("category")), student));
//		System.out.println("4 success");
		// test for getting real path for app
		System.out.println(filePath + name + imageType);// real path in local
		/*
		 * ms.sendSimpleMail("e0838388@u.nus.edu","report test","new report generated!"
		 * );
		 */
//		System.out.println("report success");
		return "report-success";}
		else return null;
	}

	@PostMapping(value = "/booking/save/{token}")
	public List<HttpStatus> newBookingAndroid(@PathVariable String token, @RequestBody List<Map<String, Object>> rawPayload) {
		
		if (JWTGenerator.verifyJWT(token)) {
		Map<String, Object> payload = rawPayload.get(0);

		Student student = ss.findStudentById(Integer.parseInt((String) payload.get("studentId")));
		Room room = rms.findRoomById((String) payload.get("roomId"));

		LocalDate date = LocalDate.parse((String) payload.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalTime time = LocalTime.parse((String) payload.get("time"));
		Booking booking = new Booking("1", date, time, Integer.parseInt((String) payload.get("duration")), room);
		booking.setStudent(student);
		List<HttpStatus> response = new ArrayList<HttpStatus>();
//		booking.setRoom(room);
//		booking.setRoom(rs.findRoomById(room.getId()));
//		booking.setRoom(rs.findRoomById(booking.getRoom().getId()));
//		List<Room> rooms = rs.findRoomsByAttributes(room);
//		List<Booking> bookings = bs.checkBookingAvailable(booking, rooms);
		if (!bs.checkBookingByDateTimeRoom(booking, room)) {
			booking.setStatus(BookingStatus.REJECTED);
			response.add(HttpStatus.EXPECTATION_FAILED);
		} else {
//			booking.setRoom(rs.findRoomById(room.getId()));
			if (booking.getStudent().getScore() >= 3) {
				booking.setStatus(BookingStatus.WAITINGLIST);
				bs.scheduleWaitingList(booking, room);
				bs.createBooking(booking);
				response.add(HttpStatus.ACCEPTED);
			} else {
				booking.setStatus(BookingStatus.SUCCESSFUL);
				bs.createBooking(booking);
				response.add(HttpStatus.ACCEPTED);
			}
		}
		return response;
		}
		else return null;
	}

}
