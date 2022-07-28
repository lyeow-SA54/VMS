package iss.team5.vms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.team5.vms.model.Booking;
import iss.team5.vms.services.BookingService;

@RestController
@RequestMapping(path = "/admin", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	@Autowired BookingService bs;
	
	@GetMapping("/bookings")
    public List<Booking> getBookings(){
        return bs.findAllBookings();
	}
}
