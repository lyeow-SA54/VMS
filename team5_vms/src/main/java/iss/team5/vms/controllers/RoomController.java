package iss.team5.vms.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import iss.team5.vms.model.Facility;
import iss.team5.vms.model.Room;
import iss.team5.vms.services.FacilityService;
import iss.team5.vms.services.RoomService;

@Controller
@RequestMapping(value = "/admin/rooms")
public class RoomController {

	@Autowired
	private RoomService rService;

	@Autowired
	private FacilityService fService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView newRoom() {
		ModelAndView mav = new ModelAndView("room-form");
		mav.addObject("room", new Room());
		List<Facility> facilities = (List<Facility>) fService.findAllFacilities();
		mav.addObject("fList", facilities);
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createRoom(@ModelAttribute @Valid Room room, BindingResult result) {
		if (result.hasErrors())
			return new ModelAndView("room-form");
		ModelAndView mav = new ModelAndView("forward:/admin/rooms/list");
		rService.createRoom(room);
		return mav;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchRoom(@RequestParam("searchRoom") String roomName,
			@RequestParam("facility") String facStr, @RequestParam("availability") String ava, Model model) {
		ModelAndView mav = new ModelAndView("rooms");
		List<Facility> facilities = (List<Facility>) fService.findAllFacilities();
		mav.addObject("checkBoxFacilities", facilities);
		mav.addObject("searchStr", roomName);
		System.out.println("Facilities" + facStr);
		if(roomName != "")
		{
			mav.addObject("rooms", rService.searchRoom(roomName, facStr, Integer.parseInt(ava)));
			for(Room r : rService.searchRoom(roomName, facStr, Integer.parseInt(ava)))
				System.out.println(r);
		}
			
		else
		{
			mav.addObject("rooms", rService.search(facStr, Integer.parseInt(ava)));
			for(Room r : rService.search(facStr, Integer.parseInt(ava)))
				System.out.println(r);
		}
			
		
		return mav;
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public ModelAndView roomList() {
		ModelAndView mav = new ModelAndView("rooms");
		List<Room> rooms = rService.findAllRooms();
		mav.addObject("rooms", rooms);
		List<Facility> facilities = (List<Facility>) fService.findAllFacilities();
		mav.addObject("checkBoxFacilities", facilities);
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editRoom(@PathVariable String id) {
		ModelAndView mav = new ModelAndView("room-edit");
		Room room = rService.findRoom(id);
		mav.addObject("room", room);
		List<Facility> facilities = (List<Facility>) fService.findAllFacilities();
		mav.addObject("fList", facilities);
		return mav;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editRoom(@ModelAttribute @Valid Room room, BindingResult result) {
		if (result.hasErrors())
			return new ModelAndView("room-edit");
		ModelAndView mav = new ModelAndView("rooms");
		rService.changeRoom(room);
		ArrayList<Room> rooms = rService.findAllRooms();
		List<Facility> facilities = (List<Facility>) fService.findAllFacilities();

		mav.addObject("checkBoxFacilities", facilities);
		mav.addObject("rooms", rooms);
		return mav;
	}

	@RequestMapping(value = "/delete/{id}")
	public ModelAndView deleteRoom(@PathVariable("id") String id) {
		ModelAndView mav = new ModelAndView("forward:/admin/rooms/list");
		Room room = rService.findRoom(id);
		rService.removeRoom(room);
		return mav;
	}
}
