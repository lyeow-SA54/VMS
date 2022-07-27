package iss.team5.vms.services;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iss.team5.vms.helper.BookingAvailablity;
import iss.team5.vms.helper.BookingSlots;
import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.repositories.BookingRepo;
import iss.team5.vms.repositories.RoomRepo;

import javax.annotation.Resource;	

@Service
public class BookingServiceImpl implements BookingService {
	@Resource
	private BookingRepo brepo;
	@Resource
	private RoomRepo rrepo;
	@Autowired
	private StudentService sService;
	@Autowired
	private RoomService rService;
	
	@Override
	@Transactional
	public ArrayList<Booking> findAllBookings() {
		// TODO Auto-generated method stub
		ArrayList<Booking> b = (ArrayList<Booking>)brepo.findAll();
		return b;
	}

	@Override
	public Booking findBooking(String id) {
		// TODO Auto-generated method stub
		return brepo.findById(id).orElse(null);
	}

	@Override
	public Booking createBooking(Booking booking) {
		// TODO Auto-generated method stub
		
		Student s = sService.findStudent(booking.getStudent().getId());
		Room rm = rService.findRoom(booking.getRoom().getId());
		
		//first come first service
		Booking bookedRoom = brepo.findBookingByRoom(rm);
		
		if (bookedRoom.getRoom().getAvailbility().name() == "OPEN" )
		{
			//scoring
			if (s.getScore() < 3)
			{
				booking.setStatus(BookingStatus.SUCCESSFUL);
				
				booking.setStudent(s);
				booking.setRoom(rm);
				booking.setRoom(rm);
			}
			else if (s.getScore() == 3)
			{
				booking.setStatus(BookingStatus.WAITINGLIST);
								
				ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
				Runnable setStatus = () -> {booking.setStatus(BookingStatus.SUCCESSFUL);};
				if (bookedRoom.getRoom().getAvailbility().name() == "OPEN" ) {
				executorService.schedule(setStatus, 1, TimeUnit.HOURS);
				rm.setAvailbility(BookingAvailablity.BOOKED);
				booking.setStudent(s);
				booking.setRoom(rm);
				}
			}
		}
		else
		{booking.setStatus(BookingStatus.REJECTED);}
		
		return brepo.saveAndFlush(booking);
	}

	@Override
	public Booking changeBooking(Booking booking) {
		// TODO Auto-generated method stub
		Booking originalBooking = findBooking(booking.getId());
		if (booking.getRoom().getAvailbility().name() == "OPEN" )
		{
		originalBooking.setRoom(booking.getRoom());
		originalBooking.setDate(booking.getDate());
		}
		else
		{originalBooking.setStatus(BookingStatus.REJECTED);}
		return brepo.saveAndFlush(originalBooking);
	}

	@Override
	public void removeBooking(Booking booking) {
		// TODO Auto-generated method stub
		
		brepo.delete(booking);
		brepo.flush();
	}

}
