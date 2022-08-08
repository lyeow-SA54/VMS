package iss.team5.vms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import iss.team5.vms.model.Facility;
import iss.team5.vms.model.Room;
import iss.team5.vms.repositories.RoomRepo;

@Service
public class RoomServiceImpl implements RoomService {

	@Resource
	private RoomRepo rrepo;

	public boolean tableExist() {
		return rrepo.existsBy();
	}

	@Override
	@Transactional
	public List<Room> findAllRooms() {
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
		r.setCapacity(room.getCapacity());
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
		return rrepo.findAll().stream().filter(froom -> (froom.getCapacity() >= room.getCapacity())
				&& (compareFacilityLists(froom.getFacilities(),room.getFacilities()))).collect(Collectors.toList());
	}

	@Override
	public boolean compareFacilityLists(List<Facility> facilities1, List<Facility> facilities2) {
		if (facilities1.size() == facilities2.size()) {		
			return facilities1.stream().allMatch(f -> facilities2.contains(f));
		}
		return false;
	}

	@Override
	public ArrayList<Room> searchRoomByNameFacilityAvailability(String roomName, String facStr, boolean ava) {
		return rrepo.searchRoomByNameFacilityAvailability(roomName, facStr, ava);
	}

	@Override
	public ArrayList<Room> searchRoomByFacilityAvailability(String facStr, boolean ava) {
		return rrepo.searchRoomByFacilityAvailability(facStr, ava);
	}

	@Override
	public Room findRoomByRoomName(String name) {
		return rrepo.findRoomByRoomName(name);
	}

}
