package iss.team5.vms.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import iss.team5.vms.helper.BookingAvailablity;
import iss.team5.vms.helper.BookingSlots;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;

public interface RoomRepo extends JpaRepository<Room,String>{
	
	@Query
	
	ArrayList<Room> findAllByRoomAvailability(Booking booking);
	
}
