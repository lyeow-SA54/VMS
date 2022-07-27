package iss.team5.vms.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.team5.vms.model.Booking;

public interface BookingRepo extends JpaRepository<Booking, String> {

	ArrayList<Booking> findAllById(String id);
	
}
