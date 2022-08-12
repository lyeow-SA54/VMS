package iss.team5.vms.model;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor

public class Student {
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_id_gen")
//	@GenericGenerator(name = "custom_id_gen", strategy = "iss.team5.vms.helper.IdGenerator", parameters = {
//			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
//			@Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "S"),
//			@Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_gen")
	@GenericGenerator(name = "student_id_gen", strategy = "iss.team5.vms.generators.StudentIdGenerator")
	private String id;
	private int score;
	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	public Student(String firstName, String lastName, String email, String username) {
		this.user = new User(firstName, lastName, email, username);
	}
}
