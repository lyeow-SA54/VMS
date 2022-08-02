package iss.team5.vms.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import iss.team5.vms.model.Facility;

public interface FacilityRepo extends JpaRepository<Facility, Integer> {
	boolean existsBy();
	
	List<Facility> findByName(String name);
}
