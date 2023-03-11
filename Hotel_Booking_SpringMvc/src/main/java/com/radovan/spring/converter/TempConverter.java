package com.radovan.spring.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.radovan.spring.dto.GuestDto;
import com.radovan.spring.dto.NoteDto;
import com.radovan.spring.dto.ReservationDto;
import com.radovan.spring.dto.RoleDto;
import com.radovan.spring.dto.RoomCategoryDto;
import com.radovan.spring.dto.RoomDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.entity.GuestEntity;
import com.radovan.spring.entity.NoteEntity;
import com.radovan.spring.entity.ReservationEntity;
import com.radovan.spring.entity.RoleEntity;
import com.radovan.spring.entity.RoomCategoryEntity;
import com.radovan.spring.entity.RoomEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.repository.GuestRepository;
import com.radovan.spring.repository.RoleRepository;
import com.radovan.spring.repository.RoomCategoryRepository;
import com.radovan.spring.repository.RoomRepository;
import com.radovan.spring.repository.UserRepository;

public class TempConverter {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoomCategoryRepository roomCategoryRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	private ModelMapper mapper;

	public GuestDto guestEntityToDto(GuestEntity guestEntity) {
		GuestDto returnValue = mapper.map(guestEntity, GuestDto.class);
		Optional<UserEntity> userEntity = Optional.ofNullable(guestEntity.getUser());
		if (userEntity.isPresent()) {
			returnValue.setUserId(userEntity.get().getId());
		}

		return returnValue;
	}

	public GuestEntity guestDtoToEntity(GuestDto guest) {
		GuestEntity returnValue = mapper.map(guest, GuestEntity.class);
		Optional<Integer> userId = Optional.ofNullable(guest.getUserId());
		if (userId.isPresent()) {
			UserEntity userEntity = userRepository.getById(userId.get());
			returnValue.setUser(userEntity);
		}

		return returnValue;
	}

	public RoomDto roomEntityToDto(RoomEntity roomEntity) {
		RoomDto returnValue = mapper.map(roomEntity, RoomDto.class);
		Optional<RoomCategoryEntity> category = Optional.ofNullable(roomEntity.getRoomCategory());
		if (category.isPresent()) {
			returnValue.setRoomCategoryId(category.get().getRoomCategoryId());
		}
		return returnValue;
	}

	public RoomEntity roomDtoToEntity(RoomDto room) {
		RoomEntity returnValue = mapper.map(room, RoomEntity.class);
		Optional<Integer> categoryId = Optional.ofNullable(room.getRoomCategoryId());
		if (categoryId.isPresent()) {
			RoomCategoryEntity categoryEntity = roomCategoryRepository.getById(categoryId.get());
			returnValue.setRoomCategory(categoryEntity);
		}
		return returnValue;
	}

	public RoomCategoryDto roomCategoryEntityToDto(RoomCategoryEntity categoryEntity) {
		RoomCategoryDto returnValue = mapper.map(categoryEntity, RoomCategoryDto.class);
		return returnValue;
	}

	public RoomCategoryEntity roomCategoryDtoToEntity(RoomCategoryDto category) {
		RoomCategoryEntity returnValue = mapper.map(category, RoomCategoryEntity.class);
		return returnValue;
	}

	public ReservationDto reservationEntityToDto(ReservationEntity reservation) {
		ReservationDto returnValue = mapper.map(reservation, ReservationDto.class);

		Optional<RoomEntity> roomEntity = Optional.ofNullable(reservation.getRoom());
		if (roomEntity.isPresent()) {
			returnValue.setRoomId(roomEntity.get().getRoomId());
		}

		Optional<GuestEntity> guestEntity = Optional.ofNullable(reservation.getGuest());
		if (guestEntity.isPresent()) {
			returnValue.setGuestId(guestEntity.get().getGuestId());
		}

		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		Optional<Timestamp> checkInDate = Optional.ofNullable(reservation.getCheckInDate());
		if (checkInDate.isPresent()) {
			String checkInDateStr = checkInDate.get().toLocalDateTime().format(formatter);
			returnValue.setCheckInDateStr(checkInDateStr);
		}

		Optional<Timestamp> checkOutDate = Optional.ofNullable(reservation.getCheckOutDate());
		if (checkOutDate.isPresent()) {
			String checkOutDateStr = checkOutDate.get().toLocalDateTime().format(formatter);
			returnValue.setCheckOutDateStr(checkOutDateStr);
		}

		return returnValue;
	}

	public ReservationEntity reservationDtoToEntity(ReservationDto reservation) {
		ReservationEntity returnValue = mapper.map(reservation, ReservationEntity.class);

		Optional<Integer> roomId = Optional.ofNullable(reservation.getRoomId());
		if (roomId.isPresent()) {
			RoomEntity roomEntity = roomRepository.getById(roomId.get());
			returnValue.setRoom(roomEntity);
		}

		Optional<Integer> guestId = Optional.ofNullable(reservation.getGuestId());
		if (guestId.isPresent()) {
			GuestEntity guestEntity = guestRepository.getById(guestId.get());
			returnValue.setGuest(guestEntity);
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		Optional<String> checkInDateStrOpt = Optional.ofNullable(reservation.getCheckInDateStr());
		if (checkInDateStrOpt.isPresent()) {
			String checkInDateStr = checkInDateStrOpt.get();

			LocalDateTime checkInDate = LocalDateTime.parse(checkInDateStr, formatter);
			returnValue.setCheckInDate(Timestamp.valueOf(checkInDate));
		}

		Optional<String> checkOutDateStrOpt = Optional.ofNullable(reservation.getCheckOutDateStr());
		if (checkOutDateStrOpt.isPresent()) {
			String checkOutDateStr = checkOutDateStrOpt.get();
			LocalDateTime checkOutDate = LocalDateTime.parse(checkOutDateStr, formatter);
			returnValue.setCheckOutDate(Timestamp.valueOf(checkOutDate));
		}

		return returnValue;
	}

	public NoteDto noteEntityToDto(NoteEntity noteEntity) {
		NoteDto returnValue = mapper.map(noteEntity, NoteDto.class);
		Optional<Timestamp> createdAt = Optional.ofNullable(noteEntity.getCreatedAt());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		if (createdAt.isPresent()) {
			String createdAtStr = String.valueOf(createdAt.get().toLocalDateTime().format(formatter));
			returnValue.setCreatedAtStr(createdAtStr);
		}

		return returnValue;
	}

	public NoteEntity noteDtoToEntity(NoteDto noteDto) {
		NoteEntity returnValue = mapper.map(noteDto, NoteEntity.class);
		return returnValue;
	}

	public UserDto userEntityToDto(UserEntity userEntity) {
		UserDto returnValue = mapper.map(userEntity, UserDto.class);
		returnValue.setEnabled(userEntity.getEnabled());
		Optional<List<RoleEntity>> roles = Optional.ofNullable(userEntity.getRoles());
		List<Integer> rolesIds = new ArrayList<Integer>();

		if (!roles.isEmpty()) {
			for (RoleEntity roleEntity : roles.get()) {
				rolesIds.add(roleEntity.getId());
			}
		}

		returnValue.setRolesIds(rolesIds);

		return returnValue;
	}

	public UserEntity userDtoToEntity(UserDto userDto) {
		UserEntity returnValue = mapper.map(userDto, UserEntity.class);
		List<RoleEntity> roles = new ArrayList<>();
		Optional<List<Integer>> rolesIds = Optional.ofNullable(userDto.getRolesIds());

		if (!rolesIds.isEmpty()) {
			for (Integer roleId : rolesIds.get()) {
				RoleEntity role = roleRepository.getById(roleId);
				roles.add(role);
			}
		}

		returnValue.setRoles(roles);

		return returnValue;
	}

	public RoleDto roleEntityToDto(RoleEntity roleEntity) {
		RoleDto returnValue = mapper.map(roleEntity, RoleDto.class);
		Optional<List<UserEntity>> users = Optional.ofNullable(roleEntity.getUsers());
		List<Integer> userIds = new ArrayList<>();

		if (!users.isEmpty()) {
			for (UserEntity user : users.get()) {
				userIds.add(user.getId());
			}
		}

		returnValue.setUserIds(userIds);
		return returnValue;
	}

	public RoleEntity roleDtoToEntity(RoleDto roleDto) {
		RoleEntity returnValue = mapper.map(roleDto, RoleEntity.class);
		Optional<List<Integer>> usersIds = Optional.ofNullable(roleDto.getUserIds());
		List<UserEntity> users = new ArrayList<>();

		if (!usersIds.isEmpty()) {
			for (Integer userId : usersIds.get()) {
				UserEntity userEntity = userRepository.getById(userId);
				users.add(userEntity);
			}
		}
		returnValue.setUsers(users);
		return returnValue;
	}
}
