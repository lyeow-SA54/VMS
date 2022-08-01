package iss.team5.vms.services;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import iss.team5.vms.model.Room;
import iss.team5.vms.repositories.RoomRepo;

@Service
public class RoomServiceImpl implements RoomService{
	
	@Resource
	private RoomRepo rrepo;
	
	@Override
	@Transactional
	public ArrayList<Room> findAllRooms(){
		ArrayList<Room> r = (ArrayList<Room>)rrepo.findAll();
		return r;
	}
	
	@Transactional
	public Room findRoom(String id) {
		return rrepo.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public Room createRoom(Room room) {
		return rrepo.saveAndFlush(room);
	}
	
	@Override
	@Transactional
	public void changeRoom(Room room) {
		Room r = findRoom(room.getId());
//		r.setAvailability(room.getAvailability());
		r.setFacilities(room.getFacilities());
		r.setCapacity(room.getCapacity());
		r.setRoomName(room.getRoomName());
		rrepo.saveAndFlush(r);
	}
	
	@Override
	@Transactional
	public void removeRoom(Room room) {
		rrepo.delete(room);
		rrepo.flush();
	}

	@Override
	public ArrayList<Room> searchRoom(String roomName, String facStr, int ava) {
		return rrepo.searchRoom(roomName, facStr, ava);
	}

	@Override
	public ArrayList<Room> search(String facStr, int ava) {
		return rrepo.search(facStr, ava);
	}

}
