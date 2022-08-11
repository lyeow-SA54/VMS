package iss.team5.vms.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.helper.ReportStatus;
import iss.team5.vms.model.Report;
import iss.team5.vms.model.User;
import iss.team5.vms.services.ReportService;
import iss.team5.vms.services.UserSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iss.team5.vms.model.Booking;
import iss.team5.vms.services.BookingService;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path = "/admin", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public class RestAdminController {

	@Autowired
	BookingService bs;

	@Autowired
	ReportService rs;

	@Autowired
	private UserSessionService userSessionService;

//	@GetMapping("/bookings")
//    public List<Booking> getBookings(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        return bs.findAllBookings();
//	}

	@GetMapping("/bookings")
	public ResponseEntity<?> getBookings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = userSessionService.findUserBySession();
		if (user == null) {
			// react need to redirect to login page (window.location.reload something like
			// that), if it gets 401 response
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok().body(bs.findAllBookings());
	}

	@GetMapping("/reports")
	public List<Report> getReports() {
		List<Report> reports = rs.findAllReports();
		reports.stream().forEach(r -> r.setImgPath("/img/" + r.getImgPath()));
		return reports;
	}

	@PostMapping(value = "/bookings/{id}")
	public ResponseEntity cancelBooking(@PathVariable String id) {
		try {
			bs.cancelCourseById(id);
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
		ModelAndView mav = new ModelAndView("admin-index");
		return mav;
	}
}
