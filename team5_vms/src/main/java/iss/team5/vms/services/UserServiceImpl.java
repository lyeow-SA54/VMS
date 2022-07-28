package iss.team5.vms.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import iss.team5.vms.model.Role;
import iss.team5.vms.model.User;
import iss.team5.vms.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserRepo urepo;
	
	public boolean tableExist() {
		return urepo.existsBy();
	}
	
	@Override
	public ArrayList<User> findAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public User createAdmin(User user) {
		// TODO Auto-generated method stub
		return null;
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
