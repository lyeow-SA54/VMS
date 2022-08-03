package iss.team5.vms.services;

import java.util.ArrayList;

import iss.team5.vms.model.User;

public interface UserService {
	
	boolean tableExist();
	
	ArrayList<User> findAllUsers();
	
	User findUser(String id);

	User createUser(User user);
	
	User changeUser(User user);

	void removeUser(User user);	
	

}
