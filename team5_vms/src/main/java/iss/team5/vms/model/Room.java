package iss.team5.vms.model;

import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_id_gen")
	@GenericGenerator(name = "room_id_gen", strategy = "iss.team5.vms.generators.RoomIdGenerator")
	private String id;
	private boolean availability;
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "room_facilities",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
            )
    private List<Facility> facilities = new ArrayList<>();
	
	private String roomName;
	private int capacity;
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime blockedStartTime;
	private int blockDuration;
	
	public Room(String id)
	{
		this.id = id;
	}
	
	public Room(String id, int capacity, List<Facility> facilities)
	{
		this.capacity = capacity;
		this.id = id;
		this.facilities = facilities;
	}
	
	public Room(boolean availability, String roomName, int capacity) {
		this.availability=availability;
		this.roomName=roomName;
		this.capacity=capacity;
	}
	
	public Room(String roomName, List<Facility> facList, int capacity, boolean availability, LocalTime startTime, int duration) {
		this.roomName=roomName;
		facilities = facList;
		this.availability=availability;
		this.capacity=capacity;
		blockedStartTime = startTime;
		blockDuration = duration;
	}
}
