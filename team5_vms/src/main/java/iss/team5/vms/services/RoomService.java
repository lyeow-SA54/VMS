package iss.team5.vms.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import iss.team5.vms.model.Facility;
import iss.team5.vms.model.Room;

public interface RoomService {
	
	boolean tableExist();
	
	List<Room> findAllRooms();
	
	Room findRoomById(String id);

	Room createRoom(Room room);

	void changeRoom(Room room);

	void removeRoom(Room room);	
	
	Room findRoomByRoomName(String name);
	
	List<Room> findRoomsByAttributes(Room room);
	
	ArrayList<Room> searchRoomByNameFacilityAvailability(String roomName, String facStr, boolean ava);
	
	ArrayList<Room> searchRoomByFacilityAvailability(String facStr, boolean ava);

	boolean compareFacilityLists(List<Facility> facilities1, List<Facility> facilities2);
}
