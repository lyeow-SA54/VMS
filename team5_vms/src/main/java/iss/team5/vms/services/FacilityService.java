package iss.team5.vms.services;

import java.util.List;

import iss.team5.vms.model.Facility;

public interface FacilityService {
	boolean tableExist();
	
	List<Facility> findAllFacilities();
	
	void createFacility(Facility facility);
	
	List<Facility> findFacilityByName(String name);
}
