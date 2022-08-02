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
	
	List<Room> findRoomsByAttributes(Room room);
	
	ArrayList<Room> searchRoom(String roomName, String facStr, int ava);
	
	ArrayList<Room> search(String facStr, int ava);

	boolean compareRoomAndFacilities(List<Facility> facilities1, List<Facility> facilities2);
}
