package iss.team5.vms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.team5.vms.model.Student;
import iss.team5.vms.model.User;


public interface StudentRepo extends JpaRepository<Student, String>{

	Boolean existsBy();
	
	Student findStudentByUser(User user);
}