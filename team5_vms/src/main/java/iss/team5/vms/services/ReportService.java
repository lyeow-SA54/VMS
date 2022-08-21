package iss.team5.vms.services;

import java.time.LocalDate;
import java.util.List;

import iss.team5.vms.helper.ReportCategory;
import iss.team5.vms.helper.ReportStatus;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Report;
import iss.team5.vms.model.Room;

public interface ReportService {
	
	boolean tableExist();
	
	boolean checkMultipleReports(Report report);
	
	Report findReportById(String id);

	Report createReport(Report report);

	Report changeReport(Report report);

	void addBooking(Report report,Booking booking);
	
	void dailyBookingCheckinScoring();
	
	void approveReportScoring(Report report);
	
	void weeklyScoringUpdate();

	int getReportStatusCounts(List<Report> report, ReportStatus status);

	int getReportCatCounts(List<Report> report, ReportCategory category);

	int getReportRoomCounts(List<Report> report, Room room);
	
//	ArrayList<Report> findAllReportByStudent(Student student);

	List<Report> findReportsInCurrentWeek(LocalDate date);
	
	List<Report> findAllReports();
    
}
