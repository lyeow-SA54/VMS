package iss.team5.vms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import iss.team5.vms.helper.MyUserDetails;
import iss.team5.vms.model.User;
import iss.team5.vms.repositories.UserRepo;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepo urepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = urepo.getUserByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("Could not find user");
		}
		
		return new MyUserDetails(user);
	}

}
