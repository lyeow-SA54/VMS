package iss.team5.vms.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	public User(String firstName, String email, String username, String password)
	{
		this.firstName = firstName;
		this.email = email;
		this.username = username;
		this.password = password;
	}
}
