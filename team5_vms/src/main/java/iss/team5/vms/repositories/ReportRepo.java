package iss.team5.vms.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Report;
import iss.team5.vms.model.Student;



public interface ReportRepo extends JpaRepository<Report,String>{
	
	Boolean existsBy();
	
	ArrayList<Report> findAllReportByStudent(Student student);

	Report findReportByBooking(Booking booking);
    
}
