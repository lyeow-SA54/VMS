package iss.team5.vms.controllers;

import iss.team5.vms.helper.ReportStatus;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Report;
import iss.team5.vms.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/ar")
public class AdminReportController {
    @Autowired
    ReportService rs;

    @RequestMapping("/reports")
    public ModelAndView getReports() {
        List<Report> reports = rs.findAllReports();
        ModelAndView mav = new ModelAndView("reports");
        mav.addObject("reports",reports);
        /*Report report = rs.findReportById("RE00021");
        ModelAndView mav = new ModelAndView("reports");
        mav.addObject("reports",report);*/
        return mav;
    }
    @RequestMapping(value = "/reports/reject/{reportId}", method = RequestMethod.GET)
    public String rejectReport(@PathVariable String reportId) {
        Report report = rs.findReportById(reportId);
        report.setReportStatus(ReportStatus.REJECTED);
        rs.createReport(report);
        System.out.println("5 success");
        return "forward:/ar/reports";
    }
    @RequestMapping(value = "/reports/approval/{reportId}", method = RequestMethod.GET)
    public String approvalReport(@PathVariable String reportId) {
        Report report = rs.findReportById(reportId);
        report.setReportStatus(ReportStatus.APPROVED);
        rs.createReport(report);
        System.out.println("6 success");
        return "forward:/ar/reports";
    }
}
