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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;

	public Facility(String name) {
		this.name = name;
	}

	@Override
    public boolean equals(Object obj) {
//        if (obj == null || this.getName() == null || this.getName().equals(""))  {
//            return false;
//        }
//
//        if (obj.getClass() != this.getClass()) {
//            return false;
//        }
		System.out.println("in equals method");
        Facility other = (Facility) obj;
        if(this.getName().equals(other.getName())) {
        	System.out.println("is equal");
        	return true;
        	
        }
        
        
//        if ((this.getName() == null) ? (other.getName() != null) : !this.name.equals(other.name)) {
//            return false;
//        }
        System.out.println("not equal");
        return false;
    }

	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
