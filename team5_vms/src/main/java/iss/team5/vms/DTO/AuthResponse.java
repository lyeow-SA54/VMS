package iss.team5.vms.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
	private String username;
	private String accessToken;
	public AuthResponse(String username, String accessToken) {
		this.username = username;
		this.accessToken = accessToken;
	}
	
}
