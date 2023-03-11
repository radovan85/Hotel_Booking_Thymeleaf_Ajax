package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.GuestDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.entity.GuestEntity;
import com.radovan.spring.entity.RoleEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.exceptions.ExistingEmailException;
import com.radovan.spring.model.RegistrationForm;
import com.radovan.spring.repository.GuestRepository;
import com.radovan.spring.repository.RoleRepository;
import com.radovan.spring.repository.UserRepository;
import com.radovan.spring.service.GuestService;

@Service
@Transactional
public class GuestServiceImpl implements GuestService{
	
	@Autowired
	private GuestRepository guestRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private TempConverter tempConverter;

	@Override
	public GuestDto addGuest(GuestDto guest) {
		// TODO Auto-generated method stub
		GuestEntity guestEntity = tempConverter.guestDtoToEntity(guest);
		GuestEntity storedGuest = guestRepository.save(guestEntity);
		GuestDto returnValue = tempConverter.guestEntityToDto(storedGuest);
		return returnValue;
	}

	@Override
	public GuestDto getGuestById(Integer guestId) {
		// TODO Auto-generated method stub
		GuestDto returnValue = null;
		Optional<GuestEntity> guestEntity = 
				Optional.ofNullable(guestRepository.getById(guestId));
		if(guestEntity.isPresent()) {
			returnValue = tempConverter.guestEntityToDto(guestEntity.get());
		}
		return returnValue;
	}

	@Override
	public void deleteGuest(Integer guestId) {
		// TODO Auto-generated method stub
		guestRepository.deleteById(guestId);
		guestRepository.flush();
	}

	@Override
	public List<GuestDto> listAll() {
		// TODO Auto-generated method stub
		List<GuestDto> returnValue = new ArrayList<GuestDto>();
		Optional<List<GuestEntity>> allGuests = 
				Optional.ofNullable(guestRepository.findAll());
		if(!allGuests.isEmpty()) {
			for(GuestEntity guest:allGuests.get()) {
				GuestDto guestDto = tempConverter.guestEntityToDto(guest);
				returnValue.add(guestDto);
			}
		}
		return returnValue;
	}

	@Override
	public GuestDto storeGuest(RegistrationForm form) {
		// TODO Auto-generated method stub
		
		UserDto userDto = form.getUser();
		Optional<UserEntity> testUser = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
		if (testUser.isPresent()) {
			Error error = new Error("Email exists");
			throw new ExistingEmailException(error);
		}

		RoleEntity role = roleRepository.findByRole("ROLE_USER");
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userDto.setEnabled((byte) 1);
		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		roles.add(role);
		UserEntity userEntity = tempConverter.userDtoToEntity(userDto);
		userEntity.setRoles(roles);
		userEntity.setEnabled((byte) 1);
		UserEntity storedUser = userRepository.save(userEntity);
		List<UserEntity> users = new ArrayList<UserEntity>();
		users.add(storedUser);
		role.setUsers(users);
		roleRepository.saveAndFlush(role);

		
		GuestDto guest = form.getGuest();
		guest.setUserId(storedUser.getId());

		GuestEntity guestEntity = tempConverter.guestDtoToEntity(guest);
		GuestEntity storedGuest = guestRepository.save(guestEntity);
		GuestDto returnValue = tempConverter.guestEntityToDto(storedGuest);

		return returnValue;
	}

	@Override
	public GuestDto getGuestByUserId(Integer userId) {
		// TODO Auto-generated method stub
		GuestDto returnValue = null;
		Optional<GuestEntity> guestEntity = Optional.ofNullable(guestRepository.findByUserId(userId));
		if(guestEntity.isPresent()) {
			returnValue = tempConverter.guestEntityToDto(guestEntity.get());
		}
		return returnValue;
	}

}
