package iss.team5.vms.services;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.transaction.Transactional;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import org.springframework.stereotype.Service;

import iss.team5.vms.model.Report;
import iss.team5.vms.model.Student;
import iss.team5.vms.repositories.ReportRepo;

@Service
public class ReportServiceImpl implements ReportService{
	
	@Resource
	private ReportRepo rprepo;
	
	public boolean tableExist() {
		return rprepo.existsBy();
	}
	
	@Override
	@Transactional
	public List<Report> findAllReports(){
		List<Report> r = rprepo.findAll();
		return r;
	}
	
	@Transactional 
	public Report findReportById(String id) {
		return rprepo.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public Report createReport(Report report) {
		return rprepo.saveAndFlush(report);
	}
	
	@Override
	@Transactional
	public Report changeReport(Report report) {
		Report r = findReportById(report.getId());
		r.setDetails(report.getDetails());
		r.setStudent(report.getStudent());
		r.setBooking(report.getBooking());
		r.setCategory(report.getCategory());
		r.setStatus(report.getStatus());
//		r.setImg(report.getImg());
		return rprepo.saveAndFlush(r);
	}
	
	@Override
	@Transactional
	public void removeReport(Report report) {
		rprepo.delete(report);
		rprepo.flush();
	}
	
	@Override
	@Transactional
	public ArrayList<Report> findAllReportByStudent(Student student){
		ArrayList<Report> r = (ArrayList<Report>)rprepo.findAllReportByStudent(student);
		return r;
	}
	@Override
	@Transactional
	public void addBooking(Report report, Booking booking) {
		report.setBooking(booking);
		rprepo.saveAndFlush(report);
	}
	
	
	
	
	
	
	
	
	
	

	
	
   
    
    
    
    
	
	
	
	
	

}
