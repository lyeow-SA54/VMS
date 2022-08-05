package iss.team5.vms.repo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import iss.team5.vms.Team5VmsApplication;
import iss.team5.vms.model.Room;
import iss.team5.vms.repositories.RoomRepo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Team5VmsApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace =
AutoConfigureTestDatabase.Replace.NONE)

public class RoomRepoTest {

	@Autowired RoomRepo rRepo;
	
	@Test
    @Order(1)
	public void testCreateRoom()  {
    	List<Room> rList = new ArrayList<Room>();
    	rList.add(new Room("Room"));
    	rList.add(new Room("R1"));
    	rList.add(new Room("R2"));
		rRepo.saveAllAndFlush(rList);
		Room r1 = rList.get(0);
		Assertions.assertNotNull(r1.getId());
    }
	
	@Test
	@Order(2)
	public void testChangeRoom() {
		Room r1= new Room("R1");
		rRepo.saveAndFlush(r1);
		r1.setAvailability(r1.isAvailability());
		r1.setFacilities(r1.getFacilities());
		r1.setRoomName(r1.getRoomName());
		r1.setBlockedStartTime(r1.getBlockedStartTime());
		r1.setBlockDuration(r1.getBlockDuration());
		Assertions.assertNotNull(r1.getId());
	}
	
	@Test
	@Order(3)
	public void testfindRoomById() {
		Room r1 = new Room("R1");
		rRepo.saveAndFlush(r1);
		Assertions.assertNotNull(r1.getId());
	}
	
	@Test
	@Order(4)
	public void testfindAllRooms() {
		List<Room> rList = new ArrayList<Room>();
		rList.add(new Room("Room"));
    	rList.add(new Room("R1"));
    	rList.add(new Room("R2"));
		rRepo.saveAllAndFlush(rList);
		Assertions.assertNotNull(rList.get(1));
	}
	
	@Test
	@Order(5)
	public void testremoveRoom() {
		List<Room> rList = new ArrayList<Room>();
		rList.add(new Room("Room"));
    	rList.add(new Room("R1"));
    	rList.add(new Room("R2"));
    	rRepo.delete(rList.get(1));
		rRepo.flush();
		Assertions.assertNotNull(rList.get(1));
	}
	
	@Test
	@Order(6)
	public void testfindRoomsByAttributes() {
		Room r1= new Room("R1");
		rRepo.saveAndFlush(r1);
		r1.setAvailability(r1.isAvailability());
		r1.setFacilities(r1.getFacilities());
		r1.setRoomName(r1.getRoomName());
		r1.setBlockedStartTime(r1.getBlockedStartTime());
		r1.setBlockDuration(r1.getBlockDuration());
		Assertions.assertNotNull(r1.getRoomName());
	}
//	
//	
//	
//	@Test
//	@Order(7)
//	public void testcompareFacilityLists() {
//		
//	}
//	
//	@Test
//	@Order(8)
//	public void testsearchRoomByNameFacilityAvailability() {
//		Room r1= new Room("R1");
//		rRepo.saveAndFlush(r1);
//		r1.setAvailability(r1.isAvailability());
//		r1.setFacilities(r1.getFacilities());
//		r1.setRoomName(r1.getRoomName());
//		Assertions.assertNotNull(r1.getFacilities());;
//	}
//	
//	@Test
//	@Order(9)
//	public void testsearchRoomByFacilityAvailablity() {
//		Room r1= new Room("R1");
//		rRepo.saveAndFlush(r1);
//		r1.setAvailability(r1.isAvailability());
//		r1.setFacilities(r1.getFacilities());
//		Assertions.assertNotNull(r1.getFacilities());;
//	}
//	
//	
//	@Test
//	@Order(10)
//	public void testfindRoomByRoomName() {
//		Room r1= new Room("R1");
//		rRepo.saveAndFlush(r1);
//		r1.setRoomName(r1.getRoomName());
//		Assertions.assertNotNull(r1.getRoomName());;
//	}

}
