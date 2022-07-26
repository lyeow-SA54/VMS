package iss.team5.vms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import iss.team5.vms.helper.Category;
import iss.team5.vms.helper.IdGenerator;
import iss.team5.vms.helper.StudentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data

public class Report {
	@Id
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_id_gen")
  @GenericGenerator(
      name = "custom_id_gen",      strategy = "sg.edu.iss.team5.helper.IdGenerator", 
      parameters = {
          @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
          @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "RE_"),
          @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String id;
	@NotBlank(message = "Please choose a date")
	private String details;
	private Student student;
	private Booking booking;
	@Column(name = "category", columnDefinition = "ENUM('CLEANLINESS','VANDALISE','HOGGING','MISUSE')")
	@Enumerated(EnumType.STRING)
	private Category category;
	@Column(name = "Student_status", columnDefinition = "ENUM('PROBATION','NORMAL')")
	@Enumerated(EnumType.STRING)
	private StudentStatus status;
	private Image img;
}
