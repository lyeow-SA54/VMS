package iss.team5.vms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<Room> findAllRooms(){
		return rrepo.findAll();
	}
	
	@Transactional
	public Room findRoomById(String id) {
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
		Room r = findRoomById(room.getId());
		r.setAvailability(room.isAvailability());
		r.setFacilities(room.getFacilities());
		r.setRoomName(room.getRoomName());
		r.setBlockedStartTime(room.getBlockedStartTime());
		r.setBlockDuration(room.getBlockDuration());
		rrepo.saveAndFlush(r);
	}
	
	@Override
	@Transactional
	public void removeRoom(Room room) {
		rrepo.delete(room);
		rrepo.flush();
	}

	@Override
	public List<Room> findRoomsByAttributes(Room room) {
		return rrepo.findAll().stream().filter(froom -> 
		(froom.getCapacity()>=room.getCapacity())
		&&(froom.getFacilities().equals(room.getFacilities()))).collect(Collectors.toList());
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
