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
	
	public boolean tableExist() {
		return rrepo.existsBy();
	}
	
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
		Room r = new Room();
		return rrepo.saveAndFlush(r);
	}
	
	@Override
	@Transactional
	public Room changeRoom(Room room) {
		Room r = findRoom(room.getId());
		r.setAvailability(r.getAvailability());
		r.setFacilities(r.getFacilities());
		r.setRoomName(r.getRoomName());
		return rrepo.saveAndFlush(r);
	}
	
	@Override
	@Transactional
	public void removeRoom(Room room) {
		rrepo.delete(room);
		rrepo.flush();
	}

}
