package iss.team5.vms.controllers;

//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.team5.vms.DTO.Account;
import iss.team5.vms.DTO.AuthResponse;
import iss.team5.vms.generators.JWTGenerator;
import iss.team5.vms.model.User;
import iss.team5.vms.services.AccountAuthenticateService;
import iss.team5.vms.services.UserSessionService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ReactAuthAPI {
//	@Autowired
//	private AccountAuthenticateService accAuthService;

	@Autowired
	private UserSessionService userSessionService;

	@RequestMapping(path = "/react/auth", produces = "application/json")
	public Map<String, Object> loginReact() {
		User user = userSessionService.findUserBySession();
		Map<String, Object> response = new HashMap<String, Object>();
		if (user != null && user.getRole().equals("ADMIN")) {
			String id = String.valueOf(user.getId());
			String accessToken = JWTGenerator.generateJWT(id, "jwtauthenticator", user.getGroupName(), 86400000);
			AuthResponse authResponse = new AuthResponse(user.getGroupName(), accessToken);
			response.put("response", authResponse.toString());
		}

		else {
			response.put("response", "NULL");
		}

		return response;

	}
}