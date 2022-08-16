package iss.team5.vms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import iss.team5.vms.model.User;

public interface UserRepo extends JpaRepository<User,String>{
	Boolean existsBy();
	
	@Query("SELECT u FROM User u WHERE u.groupName = :username")
	User getUserByUsername(@Param("username") String username);

	User getByGroupName(String username);
	
}
