package iss.team5.vms.services;

import java.util.ArrayList;

import iss.team5.vms.model.Room;

public interface RoomService {
	
	ArrayList<Room> findAllRooms();
	
	Room findRoom(String id);

	Room createRoom(Room room);

	void changeRoom(Room room);

	void removeRoom(Room room);	

}
