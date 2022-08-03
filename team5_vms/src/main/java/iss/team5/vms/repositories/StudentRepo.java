package iss.team5.vms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.team5.vms.model.Student;

public interface StudentRepo extends JpaRepository<Student,String>{
	Boolean existsBy();
	
//	<Optional>Student findById(String id);
}
