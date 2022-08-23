package iss.team5.vms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Report;

public interface ReportRepo extends JpaRepository<Report,String>{
	
	Boolean existsBy();

	Report findReportByBooking(Booking booking);
    
}
