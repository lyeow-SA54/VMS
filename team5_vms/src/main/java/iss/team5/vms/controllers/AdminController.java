package iss.team5.vms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping(value="/bookings/{id}")
    public ResponseEntity cancelBooking(@PathVariable String id){
        try{
            bs.cancelCourseById(id);
            return ResponseEntity.ok().build();
        } 
        catch (Exception e){
            return ResponseEntity.badRequest().body("Item couldnt be deleted");
        }
    }
}
