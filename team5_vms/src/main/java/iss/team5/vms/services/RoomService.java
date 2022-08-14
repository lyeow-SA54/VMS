package iss.team5.vms.services;

import java.util.ArrayList;
import java.util.List;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Facility;
import iss.team5.vms.model.Room;

public interface RoomService {
	
	boolean tableExist();

	boolean compareFacilityListsExact(List<Facility> facilities1, List<Facility> facilities2);
	
	boolean compareFacilityListsContains(List<Facility> facilities1, List<Facility> facilities2);
	
	List<Room> findAllRooms();
	
	List<Room> findRoomsByExactAttributes(Room room);
	
	List<Room> findRoomsByContainingAttributes(Room room);
	
	List<Room> findAllRoomsOpenForBooking(Booking booking, List<Room> rooms);	
	
	ArrayList<Room> searchRoomByNameFacilityAvailability(String roomName, String facStr, boolean ava);
	
	ArrayList<Room> searchRoomByFacilityAvailability(String facStr, boolean ava);
	
	Room findRoomById(String id);

	Room createRoom(Room room);
	
	Room findRoomByRoomName(String name);

	void changeRoom(Room room);

	void removeRoom(Room room);



	

	




}
