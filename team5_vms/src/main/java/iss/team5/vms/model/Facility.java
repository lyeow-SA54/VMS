package iss.team5.vms.model;

import java.util.Comparator;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	public Facility(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Facility other = (Facility) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            System.out.println("not equal");
        	return false;
        }

        return true;
    }

	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
