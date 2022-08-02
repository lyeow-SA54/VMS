package iss.team5.vms.services;

import java.util.ArrayList;

import iss.team5.vms.model.Report;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Student;

public interface ReportService {
	
	boolean tableExist();
	
	ArrayList<Report> findAllReports();
	
	Report findReportById(String id);

	Report createReport(Report report);

	Report changeReport(Report report);

	void removeReport(Report report);
	
	ArrayList<Report> findAllReportByStudent(Student student);
    
}
