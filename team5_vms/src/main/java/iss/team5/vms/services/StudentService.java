package iss.team5.vms.services;

import java.util.ArrayList;

import iss.team5.vms.model.Student;

public interface StudentService {
	
	boolean tableExist();
	
	ArrayList<Student> findAllStudents();
	
	Student findStudentById(String Id);

	Student createStudent(Student student);

	Student changeStudent(Student student);

	void removeStudent(Student student);	

}
