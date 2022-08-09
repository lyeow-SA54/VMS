package iss.team5.vms.services;

import java.security.SecureRandom;
import java.util.Map;

import iss.team5.vms.helper.Account;
import iss.team5.vms.model.Student;
import iss.team5.vms.model.User;

public interface AccountAuthenticateService {
	public User authenticateAccount(Account account);

	public String generateNewToken();

	Map<Student, String> getMap();
}
