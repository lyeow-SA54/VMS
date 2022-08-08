package iss.team5.vms.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.team5.vms.helper.Account;
import iss.team5.vms.helper.HashStringGenerator;
import iss.team5.vms.model.User;
import iss.team5.vms.repositories.UserRepo;

@Service
public class AccountAuthenticateServiceImpl implements AccountAuthenticateService {
	
	
	@Autowired
	private UserRepo urepo;

	@Override
	public User authenticateAccount(Account account) {
        if(account.getUsername().trim() == "" || account.getPassword().trim() == "") {
            return null;
        }
        User user = null;
        byte[] passwordHash = HashStringGenerator.getHash(account.getUsername(), account.getPassword());
        
        user = urepo.getUserByUsername(account.getUsername());    

        if(user == null) {
            return null;
        }

        if(Arrays.equals(user.getPassword(), passwordHash)) {
            //Return the user if password matches
            return user;
        }
        return null;
	}

}
