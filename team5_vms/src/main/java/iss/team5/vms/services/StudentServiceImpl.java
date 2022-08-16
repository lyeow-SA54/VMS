package iss.team5.vms.services;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import iss.team5.vms.generators.HashStringGenerator;
import iss.team5.vms.model.Student;
import iss.team5.vms.model.User;
import iss.team5.vms.repositories.StudentRepo;
import iss.team5.vms.repositories.UserRepo;

@Service 
public class StudentServiceImpl implements StudentService{
	
	@Resource
	private StudentRepo srepo;
	
	@Resource
	private UserRepo urepo;
	
	public boolean tableExist() {
		return srepo.existsBy();
	}
	
	@Override
	@Transactional
	public ArrayList<Student> findAllStudents(){
		ArrayList<Student> s = (ArrayList<Student>)srepo.findAll();
		return s;
	}
	
	@Override
	public Student findStudentById(String Id) {
		return srepo.findById(Id).orElse(null);
	}
	
	@Override
	@Transactional
	public Student createStudent(Student student) {
		User user = student.getUser();
		user.setRole("STUDENT");
		user.setPassword(HashStringGenerator.getHash(student.getUser().getGroupName(), "password"));
		student.setUser(user);
		return srepo.saveAndFlush(student);
	}
	
	@Override
	@Transactional
	public Student changeStudent(Student student) {
		Student s = findStudentById(student.getId());
		s.setScore(student.getScore());
		s.setUser(student.getUser());
		return srepo.saveAndFlush(s);
	}
	
	@Override
	@Transactional
	public Student updateStudent(Student student) {
		Student stu = srepo.getById(student.getId());
		stu.getUser().setFirstName(student.getUser().getFirstName());
		stu.getUser().setLastName(student.getUser().getLastName());
		return srepo.saveAndFlush(stu);
	}
	
	@Override
	@Transactional
	public void removeStudent(Student student) {
		srepo.delete(student);
		srepo.flush();
	}
	
	@Override
	public Student findStudentByUser(User user) {
		return srepo.findStudentByUser(user);
	}

	@Override
	public Student changePassword(Student student, String newPassword) {
		Student stu = srepo.getById(student.getId());
		stu.getUser().setPassword(HashStringGenerator.getHash(stu.getUser().getGroupName(), newPassword));
		srepo.saveAndFlush(stu);
		return stu;
	}
		
}
