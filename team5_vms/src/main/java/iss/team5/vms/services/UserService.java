package iss.team5.vms.services;

import iss.team5.vms.model.User;

public interface UserService {
	
	boolean tableExist();
	
	User createUser(User user);
	
	User changeUser(User user);

	void removeUser(User user);

	User findUserByUsername(String username);	

}
