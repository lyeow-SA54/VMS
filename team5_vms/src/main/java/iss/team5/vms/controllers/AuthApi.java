package iss.team5.vms.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import iss.team5.vms.helper.Account;
import iss.team5.vms.helper.AuthResponse;
import iss.team5.vms.helper.GenerateJWT;
import iss.team5.vms.model.User;
import iss.team5.vms.services.AccountAuthenticateService;

@RestController
public class AuthApi {
	@Autowired
	private AccountAuthenticateService accAuthService;
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody @Valid Account account) {
		try {
			User user = accAuthService.authenticateAccount(account);
			String id = String.valueOf(user.getId());
			String accessToken = GenerateJWT.generateJWT(id, "jwtauthenticator", account.getUsername(), 86400000);
			AuthResponse response = new AuthResponse(user.getUsername(), accessToken);
//			GenerateJWT.verifyJWT(accessToken);
			return ResponseEntity.ok().body(response);

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}