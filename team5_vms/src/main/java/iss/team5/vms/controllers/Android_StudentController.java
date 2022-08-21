package iss.team5.vms.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


import iss.team5.vms.services.*;
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
import iss.team5.vms.helper.ReportCategory;
import iss.team5.vms.helper.ReportStatus;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Report;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.model.User;

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
	MailService ms;

	@Autowired
	private AccountAuthenticateService accAuthService;

	@PostMapping(value = "/login")
	public Map<String, Object> loginAndroid(@RequestBody Account account) {
		User user = accAuthService.authenticateAccount(account);
		Map<String, Object> mapResponse = new HashMap<String, Object>();
		if (user != null) {
			String id = String.valueOf(user.getId());
			String accessToken = JWTGenerator.generateJWT(id, "jwtauthenticator", account.getUsername(), 604800000);
			Student s = ss.findStudentByUser(user);
			mapResponse.put("studentId", s.getId());
			mapResponse.put("studentName", user.getGroupName());
			mapResponse.put("studentScore", s.getScore());
			mapResponse.put("groupSize", user.getGroupSize());
			mapResponse.put("response", accessToken);
			return mapResponse;
		}
		mapResponse.put("response", "Invalid login");
		return mapResponse;
	}

	@RequestMapping("/rooms/{token}")
	public List<Room> homeAndroidRooms(@PathVariable String token, @RequestBody List<Map<String, Object>> rawPayload) {
		if (JWTGenerator.verifyJWT(token)) {
			Map<String, Object> payload = rawPayload.get(0);
			List<Room> rooms = rms.findAllRooms();
			Booking bookingForTheDay = new Booking();
			bookingForTheDay.setDate(LocalDate.now());
			bookingForTheDay.setTime(LocalTime.now());
			List<Room> availableRooms = new ArrayList<Room>();
			Student student = ss.findStudentById((String) payload.get("studentId"));
			try {
				List<Booking> availableBookings = bs.findBookingsAvailableExact(bookingForTheDay, rooms, student);
				availableRooms = availableBookings.stream().map(Booking::getRoom).distinct()
						.collect(Collectors.toList());
				return availableRooms;
			} catch (Exception e) {
				return availableRooms;
			}
		} else
			return null;

	}

	@PostMapping("/booking/history/{token}")
	public List<Booking> bookingHistoryAndroid(@PathVariable String token,
			@RequestBody List<Map<String, Object>> rawPayload) {

		if (JWTGenerator.verifyJWT(token)) {
			Map<String, Object> payload = rawPayload.get(0);
			Student s = ss.findStudentById((String) payload.get("studentId"));
			List<Booking> bookings = bs.findBookingsByStudent(s);
			return bs.updateBookingInProgress(bookings);
		} else
			return null;
	}

	@PostMapping("/booking/nearest/{token}")
	public Map<String, Object> bookingNearestAndroid(@PathVariable String token,
			@RequestBody Map<String, Object> payload) {

		if (JWTGenerator.verifyJWT(token)) {
			Student student = ss.findStudentById((String) payload.get("studentId"));
			try {
				Booking booking = bs.findStudentCurrentBooking(student);
				Map<String, Object> mapResponse = new HashMap<String, Object>();
				String response = "FOUND";
				mapResponse.put("roomName", booking.getRoom().getRoomName());
				mapResponse.put("date", booking.getDate().toString());
				mapResponse.put("time", booking.getTime().toString());
				mapResponse.put("duration", booking.getDuration());
				mapResponse.put("inprogress", booking.isBookingInProgress());
				mapResponse.put("checkin", booking.isCheckedIn());
				mapResponse.put("response", response);
				return mapResponse;
			} catch (Exception e) {
				try {
					Booking booking = bs.findStudentNextBooking(student);
					Map<String, Object> mapResponse = new HashMap<String, Object>();
					String response = "FOUND";
					mapResponse.put("roomName", booking.getRoom().getRoomName());
					mapResponse.put("date", booking.getDate().toString());
					mapResponse.put("time", booking.getTime().toString());
					mapResponse.put("duration", booking.getDuration());
					mapResponse.put("inprogress", booking.isBookingInProgress());
					mapResponse.put("checkin", booking.isCheckedIn());
					mapResponse.put("response", response);
					return mapResponse;
				} catch (Exception e2) {
					Map<String, Object> mapResponse = new HashMap<String, Object>();
					String response = "NULL";
					mapResponse.put("response", response);
					return mapResponse;
				}
			}

		} else
			return null;
	}

	@PostMapping(value = "/booking/options/{token}")
	public List<Booking> bookingOptionsAndroid(@PathVariable String token,
			@RequestBody List<Map<String, Object>> rawPayload) {

		if (JWTGenerator.verifyJWT(token)) {
			Map<String, Object> payload = rawPayload.get(0);

			LocalDate date = LocalDate.parse((String) payload.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalTime time = LocalTime.parse((String) payload.get("time"));
			Student student = ss.findStudentById((String) payload.get("studentId"));

			Booking booking = new Booking("1", date, time, Integer.parseInt((String) payload.get("duration")),
					BookingStatus.WAITINGLIST);
			Room room = new Room("1", Integer.parseInt((String) payload.get("capacity")),
					fs.jsonToFacilityList((String) payload.get("facilities")));

			List<Room> roomsExact = rms.findRoomsByExactAttributes(room);
			List<Booking> bookings = bs.findBookingsAvailableExact(booking, roomsExact, student);
			if (bookings.size() == 0) {
				List<Room> roomsContaining = rms.findRoomsByContainingAttributes(room);
				bookings.addAll(bs.findBookingsAvailableAlternative(booking, roomsExact, student));
				bookings.addAll(bs.findBookingsAvailableExact(booking, roomsContaining, student));

			}

			return bookings;
		} else
			return null;
	}

	@PostMapping(value = "/booking/extend/{token}")
	public Map<String, Object> extendBookingAndroid(@PathVariable String token,
			@RequestBody Map<String, Object> payload) {
		if (JWTGenerator.verifyJWT(token)) {

			Student student = ss.findStudentById((String) payload.get("studentId"));
			Booking currentBooking = bs.findStudentCurrentBooking(student);
			String outcomeMsg = "";
			Booking extendBooking = new Booking("placeholder", currentBooking.getDate(),
					currentBooking.getTime().plusMinutes(currentBooking.getDuration()), 1, currentBooking.getRoom());
			// check if current booking is less than 1 hour before end
			if (LocalTime.now()
					.isAfter(currentBooking.getTime().plusMinutes(currentBooking.getDuration()).minusMinutes(60))) {
				// check if extension request clashes with next booking
				if (!bs.checkBookingByDateTimeRoom(extendBooking, currentBooking.getRoom())) {
					outcomeMsg = "APPROVED";
					currentBooking.setDuration(currentBooking.getDuration() + 60);
					bs.createBooking(currentBooking);
				} else {
					Booking overlapBooking = bs.findOverlapBookingByDateTimeRoom(extendBooking,
							currentBooking.getRoom());
					int newDuration = Math.toIntExact(currentBooking.getTime().plusMinutes(currentBooking.getDuration())
							.until(overlapBooking.getTime(), ChronoUnit.MINUTES));
					if (newDuration > 10) {
						outcomeMsg = "APPROVED - EXTENDED BY " + newDuration;
						currentBooking.setDuration(currentBooking.getDuration() + newDuration);
						bs.createBooking(currentBooking);
					} else {
						outcomeMsg = "DENIED - NEXT BOOKING < 10 MINUTES AFTER CURRENT";
					}
				}
			} else {
				outcomeMsg = "DENIED - EXTENSION REQUEST TO BE MADE <1 HOUR BEFORE END OF CURRENT BOOKING";
			}
			Map<String, Object> mapResponse = new HashMap<String, Object>();
			mapResponse.put("response", outcomeMsg);
			return mapResponse;
		} else
			return null;
	}

	@PostMapping(value = "/booking/cancel/{token}")
	public Map<String, Object> cancelBookingAndroid(@PathVariable String token,
			@RequestBody Map<String, Object> payload) {
		if (JWTGenerator.verifyJWT(token)) {

			Booking booking = bs.findBookingById((String) payload.get("bookingId"));
			String outcomeMsg = "FAILED - INVALID BOOKING";

			// booking is successful and in the future
			if (booking.getStatus().equals(BookingStatus.SUCCESSFUL) && ((booking.getDate().isAfter(LocalDate.now())
					|| (booking.getDate().equals(LocalDate.now()) && booking.getTime().isAfter(LocalTime.now()))))) {
				booking.setStatus(BookingStatus.CANCELLED);
				bs.createBooking(booking);
				outcomeMsg = "COMPLETED";
			} else {
				outcomeMsg = "FAILED - INVALID BOOKING";
			}
			Map<String, Object> mapResponse = new HashMap<String, Object>();
			mapResponse.put("response", outcomeMsg);
			return mapResponse;
		} else
			return null;
	}

	@PostMapping(value = "/report/save/{token}")
	public List<Map<String, Object>> newReportAndroid(@PathVariable String token, @RequestBody List<Map<String, Object>> rawPayload) {

		if (JWTGenerator.verifyJWT(token)) {
			Map<String, Object> payload = rawPayload.get(0);
			List<Map<String, Object>> jsonArrayResponse = new ArrayList<Map<String, Object>>();
			Map<String, Object> mapResponse = new HashMap<String, Object>();

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
			Student student = ss.findStudentById((String) payload.get("studentId"));
			try {
			Booking booking = bs.findStudentCurrentBooking(student);
			Booking lastBooking = bs.findBookingBefore(booking);
			Report report = new Report((String) payload.get("details"), name + imageType, lastBooking,
					ReportStatus.PROCESSING, ReportCategory.valueOf((String) payload.get("category")), student);
			if (!rs.checkMultipleReports(report))
			{	
			rs.createReport(report);
			if (report.getCategory().equals(ReportCategory.HOGGING)) {
				if (bs.predictHogging(name + imageType)) {
					rs.approveReportScoring(report);
				}
				else {
					report.setReportStatus(ReportStatus.REJECTED);
					rs.createReport(report);
				}
			}

			mapResponse.put("response", "REPORT CREATED");
			}
			else
			mapResponse.put("response", "REPORT ALREADY MADE FOR BOOKING");
			jsonArrayResponse.add(mapResponse);
			return jsonArrayResponse;
			}
			
			catch(Exception e)
			{
				mapResponse.put("response", "NO BOOKING FOUND BEFORE CURRENT");
				jsonArrayResponse.add(mapResponse);
				return jsonArrayResponse;
			}
			

		} else
			return null;
	}

	@PostMapping(value = "/booking/save/{token}")
	public List<HttpStatus> newBookingAndroid(@PathVariable String token,
			@RequestBody List<Map<String, Object>> rawPayload) {

		if (JWTGenerator.verifyJWT(token)) {
			Map<String, Object> payload = rawPayload.get(0);

			Student student = ss.findStudentById((String) payload.get("studentId"));
			Room room = rms.findRoomById((String) payload.get("roomId"));

			LocalDate date = LocalDate.parse((String) payload.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalTime time = LocalTime.parse((String) payload.get("time"));
			Booking booking = new Booking("1", date, time, Integer.parseInt((String) payload.get("duration")), room);
			booking.setStudent(student);
			List<HttpStatus> response = new ArrayList<HttpStatus>();
			if (bs.checkBookingByDateTimeRoom(booking, room)) {
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
		} else
			return null;
	}

	@PostMapping(value = "/checkin/{bookingId}/{token}")
	public Map<String, Object> bookingCheckin(@PathVariable("bookingId") String bookingId,
			@PathVariable("token") String token, @RequestBody Map<String, Object> payload) {

		if (JWTGenerator.verifyJWT(token)) {
			try {
				Student student = ss.findStudentById((String) payload.get("studentId"));
				Booking booking = bs.findBookingById(bookingId);
				String outcomeMsg = "";
				outcomeMsg = bs.checkIn(student, booking);
				Map<String, Object> mapResponse = new HashMap<String, Object>();
				mapResponse.put("response", outcomeMsg);
				return mapResponse;
			} catch (Exception e) {
				Map<String, Object> mapResponse = new HashMap<String, Object>();
				mapResponse.put("response", "BOOKING NOT FOUND");
				return mapResponse;
			}
		} else
			return null;
	}

}