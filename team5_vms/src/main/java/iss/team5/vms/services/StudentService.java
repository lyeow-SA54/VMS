package iss.team5.vms.services;

import java.util.ArrayList;

import iss.team5.vms.model.Student;

public interface StudentService {
	
	ArrayList<Student> findAllStudents();
	
	Student findStudent(String id);

	Student createStudent(Student student);

	Student changeStudent(Student student);

	void removeStudent(Student student);	

}
