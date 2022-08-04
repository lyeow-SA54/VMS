package iss.team5.vms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.team5.vms.model.Student;
import iss.team5.vms.model.User;

public interface StudentRepo extends JpaRepository<Student, Integer>{
	Boolean existsBy();
	
	Student getStudentByUser(User user);
	
//	<Optional>Student findById(String id);
}
