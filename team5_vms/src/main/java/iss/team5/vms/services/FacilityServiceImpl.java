package iss.team5.vms.services;

import java.util.ArrayList;
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
	
	@Override
	public List<Facility> findFacilityByName(String name)
	{
		return frepo.findByName(name);
	}
	
	@Override
	public List<Facility> jsonToFacilityList(String jsonarray)
	{
		List<Facility> facilities = new ArrayList<>();
		for (int i = 0; i<3; i++)
		{
			if(jsonarray.contains(String.valueOf(i)))
			switch(i)
			{
			case 0:
				facilities.addAll(frepo.findByName("Projector"));
				break;
			case 1:
				facilities.addAll(frepo.findByName("White Board"));
				break;
			case 2:
				facilities.addAll(frepo.findByName("Computer"));
				break;
			}
		}
		return facilities;
	}

}
