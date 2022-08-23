package iss.team5.vms.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import iss.team5.vms.model.User;
import iss.team5.vms.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserRepo ur;
	
	public boolean tableExist() {
		return ur.existsBy();
	}

	@Override
	public User findUserByUsername(String username) {
		return ur.getUserByUsername(username);
	}

	@Override
	public User createUser(User user) {
		return ur.saveAndFlush(user);
	}

	@Override
	public User changeUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeUser(User user) {
		// TODO Auto-generated method stub
		
	}
	
}
