package iss.team5.vms.repositories;

import java.util.Optional;

import iss.team5.vms.model.User;

public interface UserRepo {
	
	Optional<User> findByUsername(String username);

}
