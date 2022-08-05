package iss.team5.vms.repo;

import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import iss.team5.vms.Team5VmsApplication;
import iss.team5.vms.repositories.StudentRepo;
import iss.team5.vms.model.Student;
import iss.team5.vms.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Team5VmsApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace =
AutoConfigureTestDatabase.Replace.NONE)





public class StudentRepoTest {
	
	@Autowired StudentRepo sr;
	
	@Test
	@Order(1)
	public void testCreateStudent() {
		List<Student> sList = new ArrayList<Student>();
		sList.add(new Student("S1"));
		sList.add(new Student("S2"));
		sList.add(new Student("S3"));
		sr.saveAllAndFlush(sList);
		Student s1 = sList.get(0);
		Assertions.assertNotNull(s1.getId());
	}
	
	@Test
	@Order(2)
	public void testChangeStudent() {
		Student s = new Student("S1");
		sr.saveAndFlush(s);
		s.setScore(s.getScore());
		s.setUser(s.getUser());
		Assertions.assertNotNull(s.getId());
	}
	
	@Test
	@Order(3)
	public void testfindStudentById() {
		Student s = new Student("S1");
		sr.saveAndFlush(s);
		Assertions.assertNotNull(s.getId());
	}
	
	@Test
	@Order(4)
	public void testfindAllStudents() {
		List<Student> sList = new ArrayList<Student>();
		sList.add(new Student("S1"));
		sList.add(new Student("S2"));
		sList.add(new Student("S3"));
		sr.saveAllAndFlush(sList);
		Assertions.assertNotNull(sList.get(1));
	}
	
	@Test
	@Order(5)
	public void testremoveStudent() {
		List<Student> sList = new ArrayList<Student>();
		sList.add(new Student("S1"));
		sList.add(new Student("S2"));
		sList.add(new Student("S3"));
		sr.delete(sList.get(1));
		sr.flush();
		Assertions.assertNotNull(sList.get(1));
	}
	
	@Test
	@Order(6)
	public void testfindStudentByUser() {
		Student s = new Student("S1");
		sr.saveAndFlush(s);
		User u = new User("S1");
		s.setUser(u);
		Assertions.assertNotNull(s.getUser());
	}

}
