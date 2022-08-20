package iss.team5.vms.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.model.Booking;
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
	public List<Room> findRoomsByExactAttributes(Room room) {

		List<Room> rooms = rrepo.findAll().stream().filter(froom -> (froom.getCapacity() >= room.getCapacity())
				&& (compareFacilityListsExact(froom.getFacilities(),room.getFacilities()))).collect(Collectors.toList());
		
		return rooms;
	}
	
	@Override
	public List<Room> findRoomsByContainingAttributes(Room room) {
		return rrepo.findAll().stream().filter(froom -> (froom.getCapacity() >= room.getCapacity())
				&& (compareFacilityListsContains(froom.getFacilities(),room.getFacilities()))).collect(Collectors.toList());
	}
	
	
	@Override
	public List<Room> findAllRoomsOpenForBooking(Booking booking, List<Room> rooms)
	{
		LocalTime bstart = booking.getTime();
		int duration = booking.getDuration();
		if (duration > 180)
			duration = 180;
		LocalTime bend = bstart.plusMinutes(duration);
		// rooms with no blocked timings
				List<Room> nullBlockTimeRooms = rooms.stream().filter(room -> room.getBlockedStartTime() == null && room.isAvailability())
						.collect(Collectors.toList());

				// check booking against room blocked timings if there are
				List<Room> frooms = rooms.stream().filter(room -> room.getBlockedStartTime() != null && room.isAvailability())
						.filter(room -> (room.getBlockedStartTime().isAfter(bend))
								|| (room.getBlockedStartTime().plusHours(room.getBlockDuration()).isBefore(bstart)))
						.collect(Collectors.toList());

				// all rooms that are open in requested timing
				frooms.addAll(nullBlockTimeRooms);
				
				return frooms;
	}

	@Override
	public boolean compareFacilityListsExact(List<Facility> existingRooms, List<Facility> roomToCheck) {
		if (existingRooms.size() == roomToCheck.size()) {		
			return existingRooms.stream().allMatch(f -> roomToCheck.contains(f));
		}
		return false;
	}
	
	@Override
	public boolean compareFacilityListsContains(List<Facility> existingRooms, List<Facility> roomToCheck) {
			if (roomToCheck.stream().allMatch(f -> existingRooms.contains(f)))
			{
				return true;
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
	
	@Override
	public int getRoomOpenHours()
	{
		List<Room> rooms = rrepo.findAll();
		int blockedDuration = (int)rooms.stream().filter(r->r.isAvailability()).map(Room::getBlockDuration).reduce(0, (a, b) -> a + b);
		int openDuration = (int)rooms.stream().filter(r->r.isAvailability()).count()*8*5*60;
		return openDuration-blockedDuration;
	}

}
