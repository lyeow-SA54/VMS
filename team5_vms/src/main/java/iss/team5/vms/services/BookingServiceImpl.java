package iss.team5.vms.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.repositories.BookingRepo;

@Service
public class BookingServiceImpl implements BookingService{
	
	@Autowired BookingRepo br;

	@Override
	public List<Booking> findAllBookings() {
		return br.findAll();
	}

	@Override
	public Booking findBookingById(String id) {
		return br.findById(id).get();
	}

	@Override
	@Transactional
	public Booking createBooking(Booking booking) {
		return br.saveAndFlush(booking);
	}

	@Override
	public void removeBooking(Booking booking) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Booking> findBookingsByRoom(Room room) {
		return br.findBookingByRoom(room);
	}

	@Override
	public boolean tableExist() {
		return br.existsBy();
	}

	@Override
	public List<Booking> findBookingsByStudent(Student student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStudent(Booking booking, Student student) {
		booking.setStudent(student);
		br.saveAndFlush(booking);
		
	}

	@Override
	public void addRoom(Booking booking, Room room) {
		booking.setRoom(room);
		br.saveAndFlush(booking);
		
		//Chao try
		
	}

}
