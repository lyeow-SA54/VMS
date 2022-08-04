package iss.team5.vms.controllers;

import java.util.List;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.helper.ReportStatus;
import iss.team5.vms.model.Report;
import iss.team5.vms.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import iss.team5.vms.model.Booking;
import iss.team5.vms.services.BookingService;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path = "/admin", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	@Autowired BookingService bs;

    @Autowired
    ReportService rs;
	
	@GetMapping("/bookings")
    public List<Booking> getBookings(){
        return bs.findAllBookings();
	}
	
	@PostMapping(value="/bookings/{id}")
    public ResponseEntity cancelBooking(@PathVariable String id){
        try{
            bs.cancelCourseById(id);
            return ResponseEntity.ok().build();
        } 
        catch (Exception e){
            return ResponseEntity.badRequest().body("Item couldnt be deleted");
        }
    }

    /*@RequestMapping("/reports")
    public ModelAndView getReports() {
        List<Report> reports = rs.findAllReports();
        ModelAndView mav = new ModelAndView("reports");
        mav.addObject("reports",reports);
        return mav;
    }
    @RequestMapping(value = "/reports/reject/{reportId}", method = RequestMethod.GET)
    public String rejectReport(@PathVariable String reportId) {
        Report report = rs.findReportById(reportId);
        report.setReportStatus(ReportStatus.REJECTED);
        rs.createReport(report);
        System.out.println("5 success");
        return "/admin/reports";
    }*/
	
	@GetMapping("/index")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("admin-index");
		return mav;
	}
}
