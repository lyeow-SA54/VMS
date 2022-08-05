package iss.team5.vms.repo;

import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import iss.team5.vms.Team5VmsApplication;
import iss.team5.vms.repositories.FacilityRepo;
import iss.team5.vms.model.Facility;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Team5VmsApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace =
AutoConfigureTestDatabase.Replace.NONE)

public class FacilityRepoTest {
	
	@Autowired FacilityRepo fr;
	
	@Test
	@Order(1)
	public void testCreateFacility() {
		List<Facility> fList = new ArrayList<Facility>();
		fList.add(new Facility("F1"));
		fList.add(new Facility("F2"));
		fList.add(new Facility("F3"));
		fr.saveAllAndFlush(fList);
		Facility f1 = fList.get(0);
		Assertions.assertNotNull(f1.getId());
	}
	
	@Test
	@Order(2)
	public void testfindAllFacilities() {
		List<Facility> fList = new ArrayList<Facility>();
		fList.add(new Facility("F1"));
		fList.add(new Facility("F2"));
		fList.add(new Facility("F3"));
		fr.saveAllAndFlush(fList);
		Assertions.assertNotNull(fList.get(1));
	}
	
	@Test
	@Order(3)
	public void testfindFacilityByName() {
		Facility f1 = new Facility("Theatre");
		f1.setName("Beacon");
		fr.findByName("Beacon");
		Assertions.assertNotNull(f1.getName());	
	}

}
