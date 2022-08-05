package iss.team5.vms.repo;

import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import iss.team5.vms.Team5VmsApplication;
import iss.team5.vms.repositories.UserRepo;
import iss.team5.vms.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Team5VmsApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace =
AutoConfigureTestDatabase.Replace.NONE)



public class UserRepoTest {
	
	@Autowired UserRepo ur;
	
	
//	@Test
//	@Order(1)
//	public void testCreateUser() {
//		List<User>uList = new ArrayList<User>();
//		uList.add(new User("Tom", "White","@","tw","student", "PW"));
//		uList.add(new User("Betty", "White","@","bw","student", "PW"));
//		uList.add(new User("Jane", "White","@","jw","student", "PW"));
//		ur.saveAllAndFlush(uList);
//		User u1 = uList.get(1);
//		Assertions.assertNotNull(u1.getId());
//	}
//	
//	@Test
//	@Order(2)
//	public void testChangeUser() {
//		User u1 = new User("U1");
//		ur.saveAndFlush(u1);
//		u1.setLastName("Thomas");
//		Assertions.assertNotNull(u1.getLastName());
//	}
//	
//	@Test
//	@Order(3)
//	public void testfindUserByUsername() {
//		User u1 = new User("U1");
//		u1.setUsername("Thomas");
//		ur.saveAndFlush(u1);
//		Assertions.assertNotNull(u1.getUsername());		
//	}
//	
//	@Test
//	@Order(4)
//	public void testfindAllUsers() {
//		List<User>uList = new ArrayList<User>();
//		uList.add(new User("U1"));
//		uList.add(new User("U2"));
//		uList.add(new User("U3"));
//		ur.saveAllAndFlush(uList);
//		Assertions.assertNotNull(uList.get(1));
//	}
//	
//	@Test
//	@Order(5)
//	public void testRemoveUser() {
//		List<User>uList = new ArrayList<User>();
//		uList.add(new User("U1"));
//		uList.add(new User("U2"));
//		uList.add(new User("U3"));
//		ur.delete(uList.get(1));
//		ur.flush();
//		Assertions.assertNotNull(uList.get(1));
//	}

}
