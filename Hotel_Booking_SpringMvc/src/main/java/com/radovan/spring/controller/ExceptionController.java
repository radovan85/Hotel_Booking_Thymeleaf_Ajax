package com.radovan.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.radovan.spring.exceptions.ExistingEmailException;
import com.radovan.spring.exceptions.ExistingRoomNumberException;
import com.radovan.spring.exceptions.InvalidUserException;

@ControllerAdvice
public class ExceptionController {

	@ResponseStatus
	@ExceptionHandler(ExistingEmailException.class)
	public ResponseEntity<?> handleExistingEmailException(ExistingEmailException ex) {
		return ResponseEntity.internalServerError().body("Email exists already!");
	}

	@ResponseStatus
	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<?> handleInvalidUserException(InvalidUserException ex) {
		return ResponseEntity.internalServerError().body("Invalid user!");
	}
	
	@ResponseStatus
	@ExceptionHandler(ExistingRoomNumberException.class)
	public ResponseEntity<?> handleExistingRoomNumberException(ExistingRoomNumberException ex){
		return ResponseEntity.internalServerError().body("Existing room number!");
	}
}
