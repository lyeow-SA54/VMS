package iss.team5.vms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import iss.team5.vms.helper.ReportStatus;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Report;
import iss.team5.vms.model.Student;
import iss.team5.vms.model.User;
import iss.team5.vms.services.BookingService;
import iss.team5.vms.services.MailService;
import iss.team5.vms.services.ReportService;
import iss.team5.vms.services.StudentService;
import iss.team5.vms.services.UserSessionService;

@RestController
@RequestMapping(path = "/admin", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public class React_AdminController {

	@Autowired
	BookingService bs;

	@Autowired
	ReportService rs;
	
	@Autowired
	StudentService ss;
	
	@Autowired
	MailService ms;

	@Autowired
	private UserSessionService userSessionService;

	@GetMapping("/bookings")
    public List<Booking> getBookings() {
        List <Booking> bookings = bs.findAllBookings();
        return bs.updateBookingInProgress(bookings);
	}

	@GetMapping("/reports")
	public List<Report> getReports() {
		List<Report> reports = rs.findAllReports();
		reports.stream().forEach(r -> r.setImgPath("/img/" + r.getImgPath()));
		return reports;
	}
	
	@GetMapping("/students")
	public List<Student> getStudents() {
		return ss.findAllStudents();
	}

	@PostMapping(value = "/bookings/{id}")
	public ResponseEntity cancelBooking(@PathVariable String id) {
		try {
			bs.cancelCourseById(id);
			Booking booking = bs.findBookingById(id);
			ms.sendSimpleMail(booking.getStudent().getUser().getEmail(), "BOOKING CANCELLED BY ADMIN", "Booking Date: "+booking.getDate()+ "Booking Time: "+booking.getTime());
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Item couldnt be deleted");
		}
	}

	@PostMapping(value = "/reports/reject/{reportId}")
	public ResponseEntity rejectReport(@PathVariable String reportId) {
		try {
			Report report = rs.findReportById(reportId);
			report.setReportStatus(ReportStatus.REJECTED);
			rs.createReport(report);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Report couldn't be updated");
		}
	}

	@PostMapping(value = "/reports/approval/{reportId}")
	public ResponseEntity approvalReport(@PathVariable String reportId) {
		try {
			Report report = rs.findReportById(reportId);
			report.setReportStatus(ReportStatus.APPROVED);
			rs.createReport(report);
			rs.approveReportScoring(report);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Report couldn't be updated");
		}
	}

	@GetMapping("/index")
	public ModelAndView home() {
		User user = userSessionService.findUserBySession();
		if (!user.getRole().equals("ADMIN")) {
			ModelAndView mav = new ModelAndView("unauthorized-student");
			return mav;
		}
		rs.resetWeeklyScoring();
		ModelAndView mav = new ModelAndView("admin-index");
		return mav;

	}
}
