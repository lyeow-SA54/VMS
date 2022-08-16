package iss.team5.vms.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {
	private String username;
	private String password;
	
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
