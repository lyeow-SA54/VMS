package iss.team5.vms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import iss.team5.vms.helper.IdGenerator;



@Data
@Entity

public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_id_gen")
  @GenericGenerator(
      name = "custom_id_gen",      strategy = "iss.team5.vms.helper.IdGenerator", 
      parameters = {
          @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
          @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "S_"),
          @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String Id;
	private int score;
	private User user;
	

}
