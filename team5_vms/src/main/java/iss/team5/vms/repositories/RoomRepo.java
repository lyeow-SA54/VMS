package iss.team5.vms.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import iss.team5.vms.model.Room;

public interface RoomRepo extends JpaRepository<Room,String>{
	Boolean existsBy();
	
	@Query(value = "SELECT * FROM room r WHERE r.room_name LIKE %:searchStr% or r.availability = :ava or r.id IN (SELECT rf.room_id FROM room_facilities rf WHERE r.id = rf.room_id AND rf.facility_id IN (SELECT f.id from facility f WHERE f.name = :fname))", nativeQuery = true) 
	public ArrayList<Room> searchRoom(@Param("searchStr") String searchStr, @Param("fname") String facStr, @Param("ava") int ava);
	
	@Query(value = "SELECT * FROM room r WHERE r.availability = :ava or r.id IN (SELECT rf.room_id FROM room_facilities rf WHERE r.id = rf.room_id AND rf.facility_id IN (SELECT f.id from facility f WHERE f.name = :fname))", nativeQuery = true) 
	public ArrayList<Room> search(@Param("fname") String facStr, @Param("ava") int ava);
}
