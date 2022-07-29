package iss.team5.vms.services;

import java.util.ArrayList;

import iss.team5.vms.model.Room;

public interface RoomService {
	
	boolean tableExist();
	
	ArrayList<Room> findAllRooms();
	
	Room findRoomById(String id);

	Room createRoom(Room room);

	Room changeRoom(Room room);

	void removeRoom(Room room);	

}
