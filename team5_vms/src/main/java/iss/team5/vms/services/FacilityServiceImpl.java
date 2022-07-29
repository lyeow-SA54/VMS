package iss.team5.vms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iss.team5.vms.model.Facility;
import iss.team5.vms.repositories.FacilityRepo;

@Service
public class FacilityServiceImpl implements FacilityService {

	@Autowired FacilityRepo frepo;

	@Override
	public List<Facility> findAllFacilities() {
		return frepo.findAll();
	}

	@Override
	public boolean tableExist() {
		return frepo.existsBy();
	}

	@Override
	@Transactional
	public void createFacility(Facility facility) {
		frepo.saveAndFlush(facility);
	}

}
