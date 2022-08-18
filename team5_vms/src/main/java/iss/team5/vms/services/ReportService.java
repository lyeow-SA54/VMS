package iss.team5.vms.services;

import java.util.ArrayList;
import java.util.List;

import iss.team5.vms.helper.ReportCategory;
import iss.team5.vms.helper.ReportStatus;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Report;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;

public interface ReportService {
	
	boolean tableExist();
	
	List<Report> findAllReports();
	
	Report findReportById(String id);

	Report createReport(Report report);

	Report changeReport(Report report);

	void addBooking(Report report,Booking booking);

	void removeReport(Report report);
	
	void dailyBookingCheckinScoring();
	
	void approveReportScoring(Report report);
	
	ArrayList<Report> findAllReportByStudent(Student student);
	
	void resetWeeklyScoring();

	int getReportStatusCounts(List<Report> report, ReportStatus status);

	int getReportCatCounts(List<Report> report, ReportCategory category);

	int getReportRoomCounts(List<Report> report, Room room);


    
}
