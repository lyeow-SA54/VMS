package iss.team5.vms.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import iss.team5.vms.model.Role;
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
	public Student findStudentById(int Id) {
		return srepo.findById(Id).orElse(null);
	}
	
	@Override
	@Transactional
	public Student createStudent(Student student) {
		User user = student.getUser();
		Role role = new Role("STUDENT");
		user.setRoles(List.of(role));
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
	public void removeStudent(Student student) {
		srepo.delete(student);
		srepo.flush();
	}
	
	@Override
	public Student findStudentByUser(User user) {
		return srepo.getStudentByUser(user);
	}
		
}
