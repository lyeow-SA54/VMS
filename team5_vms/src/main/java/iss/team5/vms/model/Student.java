package iss.team5.vms.model;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_gen")
	@GenericGenerator(name = "student_id_gen", strategy = "iss.team5.vms.generators.StudentIdGenerator")
	private String id;
	private int score;
	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	public Student(String firstName, String lastName, String email, String username, int groupSize) {
		this.user = new User(firstName, lastName, email, username, groupSize);
	}
}
