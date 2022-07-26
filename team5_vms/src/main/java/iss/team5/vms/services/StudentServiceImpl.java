package iss.team5.vms.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import iss.team5.vms.repositories.StudentRepo;
import iss.team5.vms.repositories.UserRepo;
import iss.team5.vms.model.Student;
import iss.team5.vms.model.User;

@Service 
public class StudentServiceImpl implements StudentService{
	
	@Resource
	private StudentRepo srepo;
	
	@Resource
	private UserRepo urepo;
	
	@Override
	@Transactional
	public ArrayList<Student> findAllStudents(){
		ArrayList<Student> s = (ArrayList<Student>)srepo.findAll();
		return s;
	}
	
	@Transactional
	public Student findStudent(String Id) {
		return srepo.findById(Id).orElse(null);
	}
	
	@Override
	@Transactional
	public Student createStudent(Student student) {
		User u=new User();
		urepo.saveAndFlush(u);
		Student s = new Student();
		s.setUser(u);
		return srepo.saveAndFlush(s);
	}
	
	@Override
	@Transactional
	public Student changeStudent(Student student) {
		Student s = findStudent(student.getId());
		s.setScore(s.getScore());
		s.setUser(s.getUser());
		return srepo.saveAndFlush(s);
	}
	
	@Override
	@Transactional
	public void removeStudent(Student student) {
		srepo.delete(student);
		srepo.flush();
	}
		
}
