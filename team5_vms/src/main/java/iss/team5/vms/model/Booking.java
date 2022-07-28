package iss.team5.vms.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.helper.IdGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_id_gen")
	  @GenericGenerator(
	      name = "custom_id_gen",      strategy = "iss.team5.vms.helper.IdGenerator", 
	      parameters = {
	          @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
	          @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "B"),
	          @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String id;
//	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	private Student student;
	@NotNull(message = "Please choose a date")
//	DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalDate date;
	@NotNull(message = "Please choose a start time")
	private LocalTime time;
	@NotNull(message = "Please choose a duration")
	private int duration;
	@Enumerated(EnumType.STRING)
	private BookingStatus status;
//	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	private Room room;
	
	public Booking (String id, LocalDate date, LocalTime time, int duration, BookingStatus status)
	{
		this.id = id;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.status= status;
	}
	
}
