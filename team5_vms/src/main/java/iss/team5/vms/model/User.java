package iss.team5.vms.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import iss.team5.vms.helper.IdGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull(message = "Name is mandatory")
	private String firstName;
	private String lastName;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Role> roles;
	@NotNull(message = "Email is mandatory")
	private String email;
	@NotNull(message = "Username is mandatory")
	private String username;
	@NotNull(message = "Password is mandatory")
	private String password;

	public User(String firstName, String lastName, String email, String username, List<Role> rolelist) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		roles = rolelist;
		this.password = "password";
//		this.password = new BCryptPasswordEncoder().encode("password");
	}
}
