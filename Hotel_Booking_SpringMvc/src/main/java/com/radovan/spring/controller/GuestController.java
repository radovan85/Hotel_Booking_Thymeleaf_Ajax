package com.radovan.spring.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.radovan.spring.dto.GuestDto;
import com.radovan.spring.dto.ReservationDto;
import com.radovan.spring.dto.RoomCategoryDto;
import com.radovan.spring.dto.RoomDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.service.GuestService;
import com.radovan.spring.service.ReservationService;
import com.radovan.spring.service.RoomCategoryService;
import com.radovan.spring.service.RoomService;
import com.radovan.spring.service.UserService;

@Controller
@RequestMapping(value = "/guests")
public class GuestController {

	@Autowired
	private UserService userService;

	@Autowired
	private GuestService guestService;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private RoomCategoryService roomCategoryService;

	@Autowired
	private RoomService roomService;

	@RequestMapping(value = "/allUserReservations", method = RequestMethod.GET)
	public String getAllUserReservations(ModelMap map) {
		UserDto user = userService.getCurrentUser();
		GuestDto guest = guestService.getGuestByUserId(user.getId());
		List<ReservationDto> allReservations = reservationService.listAllByGuestId(guest.getGuestId());
		List<RoomDto> allRooms = roomService.listAll();
		List<RoomCategoryDto> allCategories = roomCategoryService.listAll();
		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime cancelDate = currentDate.plusDays(1);
		map.put("cancelDate", cancelDate);
		map.put("allReservations", allReservations);
		map.put("allRooms", allRooms);
		map.put("allCategories", allCategories);
		map.put("recordsPerPage", 6);
		return "fragments/userReservationList :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/createReservation/{checkInDateStr}/{numberOfNights}", method = RequestMethod.GET)
	public String renderReservationForm(@PathVariable("checkInDateStr") String checkInDateStr,
			@PathVariable("numberOfNights") Integer numberOfNights, ModelMap map) {
		ReservationDto reservation = new ReservationDto();
		UserDto user = userService.getCurrentUser();
		GuestDto guest = guestService.getGuestByUserId(user.getId());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		checkInDateStr = checkInDateStr + " 14:00";
		List<RoomCategoryDto> allCategories = roomCategoryService.listAll();
		List<RoomDto> availableRooms = new ArrayList<RoomDto>();

		LocalDateTime checkInDate = LocalDateTime.parse(checkInDateStr, formatter);
		LocalDateTime checkOutDate = checkInDate;

		checkOutDate = checkOutDate.plusDays(numberOfNights);
		checkOutDate = checkOutDate.minusHours(2);

		String checkOutDateStr = checkOutDate.format(formatter);

		Timestamp checkInStamp = Timestamp.valueOf(checkInDate);
		Timestamp checkOutStamp = Timestamp.valueOf(checkOutDate);

		for (RoomCategoryDto category : allCategories) {
			List<RoomDto> rooms = roomService.listAllByCategoryId(category.getRoomCategoryId());
			for (RoomDto roomDto : rooms) {
				if (reservationService.isAvailable(roomDto.getRoomId(), checkInStamp, checkOutStamp)) {
					availableRooms.add(roomDto);
					break;
				}

			}

		}

		map.put("checkInDateStr", checkInDateStr);
		map.put("checkOutDateStr", checkOutDateStr);
		map.put("checkInDate", checkInDate);
		map.put("checkOutDate", checkOutDate);
		map.put("numberOfNights", numberOfNights);
		map.put("allCategories", allCategories);
		map.put("availableRooms", availableRooms);
		map.put("guestId", guest.getGuestId());
		map.put("reservation", reservation);

		return "fragments/reservationForm :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/createReservation/{roomId}", method = RequestMethod.POST)
	public String storeReservation(@ModelAttribute("reservation") ReservationDto reservation,
			@PathVariable("roomId") Integer roomId) {

		reservationService.addReservation(reservation, roomId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/bookReservation", method = RequestMethod.GET)
	public String bookReservation(ModelMap map) {
		ReservationDto reservation = new ReservationDto();
		LocalDate today = LocalDate.now();
		LocalDate maxDate = today.plusYears(1);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String todayStr = today.format(formatter);
		String maxDateStr = maxDate.format(formatter);

		map.put("reservation", reservation);
		map.put("todayStr", todayStr);
		map.put("maxDateStr", maxDateStr);
		return "fragments/reservationBooking :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/deleteReservation/{reservationId}", method = RequestMethod.GET)
	public String deleteReservation(@PathVariable("reservationId") Integer reservationId) {
		reservationService.deleteReservation(reservationId);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/accountDetails", method = RequestMethod.GET)
	public String getAccountDetails(ModelMap map) {
		UserDto authUser = userService.getCurrentUser();
		GuestDto guest = guestService.getGuestByUserId(authUser.getId());
		map.put("guest", guest);
		map.put("user", authUser);
		return "fragments/guestDetails :: ajaxLoadedContent";
	}
}
