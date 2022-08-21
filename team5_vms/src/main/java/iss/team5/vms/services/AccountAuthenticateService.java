package iss.team5.vms.services;

import iss.team5.vms.DTO.Account;
import iss.team5.vms.model.User;

public interface AccountAuthenticateService {
	public User authenticateAccount(Account account);

}
