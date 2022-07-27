package iss.team5.vms.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Parameter;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.helper.IdGenerator;
import lombok.Data;

@Entity
@Data

public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_id_gen")
	  @GenericGenerator(
	      name = "custom_id_gen",      strategy = "iss.team5.vms.helper.IdGenerator", 
	      parameters = {
	          @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
	          @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "BOOK_"),
	          @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String id;
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Student student;
	@NotBlank(message = "Please choose a date")
	DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalDate date;
	@Column(name = "Booking_slots", columnDefinition = "ENUM('SUCCESSFUL','REJECTED','CANCELLED','WAITINGLIST')")
	@Enumerated(EnumType.STRING)
	private BookingStatus status;
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Room room;
}
