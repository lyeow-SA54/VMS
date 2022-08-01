package iss.team5.vms.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import iss.team5.vms.helper.BookingSlots;
import iss.team5.vms.helper.IdGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_id_gen")
	  @GenericGenerator(
	      name = "custom_id_gen",      strategy = "iss.team5.vms.helper.IdGenerator", 
	      parameters = {
	          @Parameter(name = IdGenerator.INCREMENT_PARAM, value = "1"),
	          @Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "RM"),
	          @Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String id;
	private boolean availability;
	private String facilities;
	private String roomName;
	private int capacity;
	private LocalTime blockedStartTime;
	private int blockDuration;
	
	public Room(String id)
	{
		this.id = id;
	}
	
	public Room(boolean availability, String facilities, String roomName, int capacity) {
		this.availability=availability;
		this.facilities=facilities;
		this.roomName=roomName;
		this.capacity=capacity;
	}
}