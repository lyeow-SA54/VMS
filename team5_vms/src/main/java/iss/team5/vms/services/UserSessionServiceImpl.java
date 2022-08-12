package iss.team5.vms.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.team5.vms.model.User;

@Service
public class UserSessionServiceImpl implements UserSessionService {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	StudentService ss;
	
	private final String sessionIdentity = "loginUser";

	@Override
	public User findUserBySession() {
		User user = null;
		try {
			user = (User) session.getAttribute(sessionIdentity);
		}
		catch(ClassCastException e) {
			return null;
		}
		return user;
	}

	@Override
	public void setUserSession(User user) {
		session.setAttribute(sessionIdentity, user);
		session.setAttribute("student", ss.findStudentById(String.valueOf(user.getId())));
	}

	@Override
	public void removeUserSession() {
		session.removeAttribute(sessionIdentity);
	}

}
