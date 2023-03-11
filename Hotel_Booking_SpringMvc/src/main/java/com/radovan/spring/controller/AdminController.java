package com.radovan.spring.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.radovan.spring.dto.GuestDto;
import com.radovan.spring.dto.NoteDto;
import com.radovan.spring.dto.ReservationDto;
import com.radovan.spring.dto.RoomCategoryDto;
import com.radovan.spring.dto.RoomDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.service.GuestService;
import com.radovan.spring.service.NoteService;
import com.radovan.spring.service.ReservationService;
import com.radovan.spring.service.RoomCategoryService;
import com.radovan.spring.service.RoomService;
import com.radovan.spring.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private GuestService guestService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoomCategoryService roomCategoryService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private ReservationService reservationService;

	@RequestMapping(value = "/allGuests", method = RequestMethod.GET)
	public String allGuests(ModelMap map) {
		List<UserDto> allUsers = userService.listAllUsers();
		List<GuestDto> allGuests = guestService.listAll();
		map.put("allUsers", allUsers);
		map.put("allGuests", allGuests);
		map.put("recordsPerPage", 6);
		return "fragments/guestList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/guestDetails/{guestId}", method = RequestMethod.GET)
	public String getGuestDetails(@PathVariable("guestId") Integer guestId, ModelMap map) {
		GuestDto guest = guestService.getGuestById(guestId);
		UserDto user = userService.getUserById(guest.getUserId());
		map.put("user", user);
		map.put("guest", guest);
		return "fragments/guestDetails :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/deleteGuest/{guestId}", method = RequestMethod.GET)
	public String deleteGuest(@PathVariable ("guestId") Integer guestId) {
		GuestDto guest = guestService.getGuestById(guestId);
		List<ReservationDto> allReservations = reservationService.listAllByGuestId(guestId);
		for(ReservationDto reservation : allReservations) {
			reservationService.deleteReservation(reservation.getReservationId());
		}
		guestService.deleteGuest(guestId);
		userService.deleteUser(guest.getUserId());
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allRoomCategories", method = RequestMethod.GET)
	public String listAllCategories(ModelMap map) {
		List<RoomCategoryDto> allCategories = roomCategoryService.listAll();
		map.put("allCategories", allCategories);
		map.put("recordsPerPage", 5);
		return "fragments/roomCategoryList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/createRoomCategory", method = RequestMethod.GET)
	public String renderCategoryForm(ModelMap map) {
		RoomCategoryDto roomCategory = new RoomCategoryDto();
		map.put("roomCategory", roomCategory);
		return "fragments/roomCategoryForm :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/createRoomCategory", method = RequestMethod.POST)
	public String storeRoomCategory(@ModelAttribute("roomCategory") RoomCategoryDto roomCategory) {
		roomCategoryService.addCategory(roomCategory);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/categoryDetails/{categoryId}", method = RequestMethod.GET)
	public String getCategoryDetails(@PathVariable("categoryId") Integer categoryId, ModelMap map) {
		RoomCategoryDto roomCategory = roomCategoryService.getCategoryById(categoryId);
		map.put("roomCategory", roomCategory);
		return "fragments/roomCategoryDetails :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/updateRoomCategory/{categoryId}", method = RequestMethod.GET)
	public String renderCategoryUpdateForm(@PathVariable("categoryId") Integer categoryId, ModelMap map) {
		RoomCategoryDto roomCategory = new RoomCategoryDto();
		RoomCategoryDto currentCategory = roomCategoryService.getCategoryById(categoryId);
		map.put("roomCategory", roomCategory);
		map.put("currentCategory", currentCategory);
		return "fragments/updateRoomCategory :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/deleteRoomCategory/{categoryId}", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable("categoryId") Integer categoryId) {
		List<RoomDto> allRooms = roomService.listAllByCategoryId(categoryId);
		for(RoomDto room : allRooms) {
			reservationService.deleteAllByRoomId(room.getRoomId());
		}
		
		roomService.deleteAllByCategoryId(categoryId);
		roomCategoryService.deleteCategory(categoryId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allRooms", method = RequestMethod.GET)
	public String listAllRooms(ModelMap map) {
		List<RoomDto> allRooms = roomService.listAll();
		List<RoomCategoryDto> allCategories = roomCategoryService.listAll();
		map.put("allRooms", allRooms);
		map.put("allCategories", allCategories);
		map.put("recordsPerPage", 6);
		return "fragments/roomList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/createRoom", method = RequestMethod.GET)
	public String renderRoomForm(ModelMap map) {
		RoomDto room = new RoomDto();
		List<RoomCategoryDto> allCategories = roomCategoryService.listAll();
		map.put("room", room);
		map.put("allCategories", allCategories);
		return "fragments/roomForm :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/createRoom", method = RequestMethod.POST)
	public String storeRoom(@ModelAttribute("room") RoomDto room) {
		roomService.addRoom(room);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/roomDetails/{roomId}", method = RequestMethod.GET)
	public String getRoomDetails(@PathVariable("roomId") Integer roomId, ModelMap map) {
		RoomDto room = roomService.getRoomById(roomId);
		RoomCategoryDto category = roomCategoryService.getCategoryById(room.getRoomCategoryId());
		map.put("room", room);
		map.put("category", category);
		return "fragments/roomDetails :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/updateRoom/{roomId}", method = RequestMethod.GET)
	public String renderRoomUpdateForm(@PathVariable("roomId") Integer roomId, ModelMap map) {
		RoomDto room = new RoomDto();
		RoomDto currentRoom = roomService.getRoomById(roomId);
		List<RoomCategoryDto> allCategories = roomCategoryService.listAll();
		map.put("room", room);
		map.put("currentRoom", currentRoom);
		map.put("allCategories", allCategories);
		return "fragments/updateRoom :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/deleteRoom/{roomId}", method = RequestMethod.GET)
	public String deleteRoom(@PathVariable("roomId") Integer roomId) {
		reservationService.deleteAllByRoomId(roomId);
		roomService.deleteRoom(roomId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allNotes", method = RequestMethod.GET)
	public String listAllNotes(ModelMap map) {
		List<NoteDto> allNotes = noteService.listAll();
		map.put("allNotes", allNotes);
		map.put("recordsPerPage", 10);
		return "fragments/noteList :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/allNotesToday", method = RequestMethod.GET)
	public String listAllNotesForToday(ModelMap map) {
		List<NoteDto> allNotes = noteService.listAllForToday();
		map.put("allNotes", allNotes);
		map.put("recordsPerPage", 10);
		return "fragments/noteList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/noteDetails/{noteId}", method = RequestMethod.GET)
	public String getNoteDetails(@PathVariable("noteId") Integer noteId, ModelMap map) {
		NoteDto note = noteService.getNoteById(noteId);
		map.put("note", note);
		return "fragments/noteDetails :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/deleteNote/{noteId}", method = RequestMethod.GET)
	public String deleteNote(@PathVariable("noteId") Integer noteId) {
		noteService.deleteNote(noteId);
		return "fragments/homePage :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/deleteAllNotes",method = RequestMethod.GET)
	public String deleteAllNotes() {
		noteService.deleteAllNotes();
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allReservations", method = RequestMethod.GET)
	public String listAllReservations(ModelMap map) {
		List<ReservationDto> allReservations = reservationService.listAll();
		List<GuestDto> allGuests = guestService.listAll();
		List<UserDto> allUsers = userService.listAllUsers();
		map.put("allReservations", allReservations);
		map.put("allGuests", allGuests);
		map.put("allUsers", allUsers);
		map.put("recordsPerPage", 6);
		return "fragments/reservationList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allActiveReservations", method = RequestMethod.GET)
	public String listAllActiveReservations(ModelMap map) {
		List<ReservationDto> allReservations = reservationService.listAllActive();
		List<GuestDto> allGuests = guestService.listAll();
		List<UserDto> allUsers = userService.listAllUsers();
		map.put("allReservations", allReservations);
		map.put("allGuests", allGuests);
		map.put("allUsers", allUsers);
		map.put("recordsPerPage", 6);
		return "fragments/reservationList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/allExpiredReservations", method = RequestMethod.GET)
	public String listAllExpiredReservations(ModelMap map) {
		List<ReservationDto> allReservations = reservationService.listAllExpired();
		List<GuestDto> allGuests = guestService.listAll();
		List<UserDto> allUsers = userService.listAllUsers();
		map.put("allReservations", allReservations);
		map.put("allGuests", allGuests);
		map.put("allUsers", allUsers);
		map.put("recordsPerPage", 6);
		return "fragments/reservationList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/reservationDetails/{reservationId}", method = RequestMethod.GET)
	public String getReservationDetails(@PathVariable("reservationId") Integer reservationId, ModelMap map) {
		ReservationDto reservation = reservationService.getReservationById(reservationId);
		RoomDto room = roomService.getRoomById(reservation.getRoomId());
		RoomCategoryDto category = roomCategoryService.getCategoryById(room.getRoomCategoryId());
		GuestDto guest = guestService.getGuestById(reservation.getGuestId());
		UserDto user = userService.getUserById(guest.getUserId());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String createdAtStr = reservation.getCreatedAt().toLocalDateTime().format(formatter);
		String updatedAtStr = reservation.getUpdatedAt().toLocalDateTime().format(formatter);
		map.put("room", room);
		map.put("category", category);
		map.put("reservation", reservation);
		map.put("user", user);
		map.put("createdAtStr", createdAtStr);
		map.put("updatedAtStr", updatedAtStr);
		return "fragments/reservationDetails :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/switchReservationRoom/{reservationId}", method = RequestMethod.GET)
	public String changeReservationRoom(@PathVariable("reservationId") Integer reservationId, ModelMap map) {
		ReservationDto reservation = new ReservationDto();
		ReservationDto currentReservation = reservationService.getReservationById(reservationId);
		RoomDto currentRoom = roomService.getRoomById(currentReservation.getRoomId());
		List<RoomDto> availableRooms = roomService.listAllByCategoryId(currentRoom.getRoomCategoryId());
		RoomCategoryDto category = roomCategoryService.getCategoryById(currentRoom.getRoomCategoryId());
		availableRooms.removeIf(obj -> obj.getRoomId() == currentRoom.getRoomId());
		map.put("reservation", reservation);
		map.put("currentReservation", currentReservation);
		map.put("currentRoom", currentRoom);
		map.put("availableRooms", availableRooms);
		map.put("category", category);
		return "fragments/switchRoomForm :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/updateReservation/{reservationId}", method = RequestMethod.POST)
	public String updateReservation(@ModelAttribute ReservationDto reservation,
			@PathVariable("reservationId") Integer reservationId) {
		reservationService.updateReservation(reservation, reservationId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/deleteReservation/{reservationId}", method = RequestMethod.GET)
	public String deleteReservation(@PathVariable("reservationId") Integer reservationId) {
		reservationService.deleteReservation(reservationId);
		return "fragments/homePage :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/roomNumberError",method = RequestMethod.GET)
	public String roomError() {
		return "fragments/roomNumberError :: ajaxLoadedContent";
	}

}
