package iss.team5.vms.model;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Facility {
	@Id
	private String id;
	private String name;
	public Facility(String id,String name) {
		super();
		this.id=id;
		this.name = name;
	}
}
