package iss.team5.vms.repo;

import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import iss.team5.vms.Team5VmsApplication;
import iss.team5.vms.helper.Category;
import iss.team5.vms.helper.StudentStatus;
import iss.team5.vms.repositories.ReportRepo;
import iss.team5.vms.model.Report;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Team5VmsApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace =
AutoConfigureTestDatabase.Replace.NONE)

public class ReportRepoTest {
	
	@Autowired ReportRepo rr;
	
	@Test
	@Order(1)
	public void testCreateReport() {
		List<Report>rList = new ArrayList<Report>();
		rList.add(new Report("R1", Category.CLEANLINESS,StudentStatus.NORMAL));
		rList.add(new Report("R2", Category.CLEANLINESS,StudentStatus.NORMAL));
		rList.add(new Report("R3", Category.CLEANLINESS,StudentStatus.NORMAL));
		rr.saveAllAndFlush(rList);
		Report r1 = rList.get(0);
		Assertions.assertNotNull(r1.getId());
	}
	
	@Test
	@Order(2)
	public void testChangeReport() {
		Report r1 = new Report("R1", Category.CLEANLINESS,StudentStatus.NORMAL);
		rr.saveAndFlush(r1);
		r1.setDetails(r1.getDetails());
		r1.setStudent(r1.getStudent());
		r1.setBooking(r1.getBooking());
		r1.setCategory(r1.getCategory());
		r1.setStatus(r1.getStatus());
		Assertions.assertNotNull(r1.getId());
	}
	
	@Test
	@Order(3)
	public void testfindReportById() {
		Report r1 = new Report("R1", Category.CLEANLINESS,StudentStatus.NORMAL);
		rr.saveAndFlush(r1);
		Assertions.assertNotNull(r1.getId());
	}
	
	@Test
	@Order(4)
	public void testfindAllReports() {
		List<Report> rList = new ArrayList<Report>();
		rList.add(new Report("R1", Category.CLEANLINESS,StudentStatus.NORMAL));
		rList.add(new Report("R2", Category.CLEANLINESS,StudentStatus.NORMAL));
		rList.add(new Report("R3", Category.CLEANLINESS,StudentStatus.NORMAL));
		rr.saveAllAndFlush(rList);
		Assertions.assertNotNull(rList.get(1));
	}
	
	@Test
	@Order(5)
	public void testRemoveReport() {
		List<Report> rList = new ArrayList<Report>();
		rList.add(new Report("R1", Category.CLEANLINESS,StudentStatus.NORMAL));
		rList.add(new Report("R2", Category.CLEANLINESS,StudentStatus.NORMAL));
		rList.add(new Report("R3", Category.CLEANLINESS,StudentStatus.NORMAL));
		rr.delete(rList.get(1));
		rr.flush();
		Assertions.assertNotNull(rList.get(1));
	}
	
	@Test
	@Order(6)
	public void findAllReportByStudent() {
		Report r1 = new Report("R1", Category.CLEANLINESS,StudentStatus.NORMAL);
		rr.saveAndFlush(r1);
		Student s1 = new Student("S1");
		r1.setStudent(s1);
		List<Report> reportList = rr.findAllReportByStudent(s1);
		Assertions.assertNotNull(reportList.size());
	}
}
