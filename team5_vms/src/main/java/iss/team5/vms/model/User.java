package iss.team5.vms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import iss.team5.vms.helper.Role;
import lombok.Data;


@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotBlank(message = "Name is mandatory")
	private String firstName;
	private String lastName;
	@NotBlank(message = "Role is mandatory")
	private Role role;
	@NotBlank(message = "Email is mandatory")
	private String email;
	@NotBlank(message = "Username is mandatory")
	private String username;
	@NotBlank(message = "Password is mandatory, minimum length is 6 character")
	@Length (min = 6)
	private String password;

}
