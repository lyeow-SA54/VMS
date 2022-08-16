package iss.team5.vms.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import iss.team5.vms.generators.HashStringGenerator;
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
	private String role;
	@NotNull(message = "Email is mandatory")
	private String email;
	@NotNull(message = "Groupname is mandatory")
	private String groupName;
	
	private int groupSize;
//	@NotNull(message = "Password is mandatory")
//	@Column(columnDefinition="BINARY(32) NOT NULL")
	@JsonIgnore
	private byte[] password;

	public User(String firstName, String lastName, String email, String username, int groupSize) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.groupName = username;
		this.role="STUDENT";
		this.password = HashStringGenerator.getHash(username, "password");
		this.groupSize = groupSize;
	}
}
