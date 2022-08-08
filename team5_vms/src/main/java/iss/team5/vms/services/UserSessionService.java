package iss.team5.vms.services;

import iss.team5.vms.model.User;

public interface UserSessionService {
	public User findUserBySession();
	public void setUserSession(User user);
	public void removeUserSession();
}
