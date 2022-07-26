package iss.team5.vms.services;

import java.util.ArrayList;

import iss.team5.vms.model.Report;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Student;


public interface ReportService {
	
	ArrayList<Report> findAllReports();
	
	Report findReport(String id);

	Report createReport(Report report);

	Report changeReport(Report report);

	void removeReport(Report report);
	
	ArrayList<Report> findAllReportByStudent(Student student);
	
	Report findReportByStudent(Student student);

	ArrayList<Report> findAllReportByBooking(Booking booking);	
	
    Report findReportByBooking(Booking booking);
    
    Student findStudentByReport(Report report);
    
    Booking findBookingByReport(Report report);
    
}
