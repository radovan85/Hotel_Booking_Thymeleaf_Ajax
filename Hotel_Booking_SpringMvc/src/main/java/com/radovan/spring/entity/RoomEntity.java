package com.radovan.spring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rooms")
public class RoomEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "room_id")
	private Integer roomId;

	@Column(name = "room_number")
	private Integer roomNumber;

	private Float price;

	@OneToOne
	@JoinColumn(name = "category_id")
	private RoomCategoryEntity roomCategory;

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

	public RoomCategoryEntity getRoomCategory() {
		return roomCategory;
	}

	public void setRoomCategory(RoomCategoryEntity roomCategory) {
		this.roomCategory = roomCategory;
	}

}
