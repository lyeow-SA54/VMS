package iss.team5.vms.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import iss.team5.vms.model.Room;

public interface RoomService {
	
	
	ArrayList<Room> findAllRooms();
	
	Room findRoom(String id);

	Room createRoom(Room room);

	void changeRoom(Room room);

	void removeRoom(Room room);	
	
	ArrayList<Room> searchRoom(String roomName, String facStr, int ava);
	
	ArrayList<Room> search(String facStr, int ava);
}
