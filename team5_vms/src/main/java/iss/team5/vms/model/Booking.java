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
import org.springframework.format.annotation.DateTimeFormat;

import iss.team5.vms.helper.BookingStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_id_gen")
	@GenericGenerator(name = "booking_id_gen", strategy = "iss.team5.vms.generators.BookingIdGenerator")
	private String id;
	@OneToOne
	private Student student;
	@NotNull(message = "Please choose a date")
	private LocalDate date;
	@NotNull(message = "Please choose a start time")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime time;
	@NotNull(message = "Please choose a duration")
	private int duration;
	@Enumerated(EnumType.STRING)
	private BookingStatus status;
	@OneToOne
	private Room room;
	private boolean checkedIn;
	private boolean bookingInProgress;
	private boolean validCancel;

	public Booking(String id, LocalDate date, LocalTime time, int duration, BookingStatus status, Student student) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.status = status;
		this.student = student;
	}

	public Booking(String id, LocalDate date, LocalTime time, int duration, Room room, Student student) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.room = room;
		this.student = student;
	}

	public Booking(String id, LocalDate date, LocalTime time, int duration, BookingStatus status) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.status = status;
	}

	public Booking(String id, LocalDate date, LocalTime time, int duration, Room room) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.room = room;
	}

	public Booking(String id, LocalDate date, LocalTime time, int duration) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.duration = duration;
	}

}
