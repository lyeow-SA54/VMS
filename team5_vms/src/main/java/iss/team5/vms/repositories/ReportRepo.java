package iss.team5.vms.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Report;
import iss.team5.vms.model.Student;


public interface ReportRepo extends JpaRepository<Report,String>{
	
	ArrayList<Report> findAllReports();
	
	ArrayList<Report> findAllReportByStudent(Student student);
	
	Report findReportByStudent(Student student);

	ArrayList<Report> findAllReportByBooking(Booking booking);	
	
    Report findReportByBooking(Booking booking);
    
    Student findStudentByReport(Report report);
    
    Booking findBookingByReport(Report report);
}
