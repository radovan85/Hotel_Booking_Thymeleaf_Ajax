package com.radovan.spring.exceptions;

import javax.management.RuntimeErrorException;

public class ExistingRoomNumberException extends RuntimeErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExistingRoomNumberException(Error e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

}
