package com.radovan.spring.dto;

import java.io.Serializable;

public class RoomDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer roomId;

	private Integer roomNumber;

	private Float price;

	private Integer roomCategoryId;

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getRoomCategoryId() {
		return roomCategoryId;
	}

	public void setRoomCategoryId(Integer roomCategoryId) {
		this.roomCategoryId = roomCategoryId;
	}

	@Override
	public String toString() {
		return "RoomDto [roomId=" + roomId + ", roomNumber=" + roomNumber + ", price=" + price + ", roomCategoryId="
				+ roomCategoryId + "]";
	}
	
	

}
