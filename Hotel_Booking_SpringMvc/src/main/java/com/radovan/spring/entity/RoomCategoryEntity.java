package com.radovan.spring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room_categories")
public class RoomCategoryEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer roomCategoryId;
	
	private String name;
	
	private Float price;
	
	private Byte wifi;

	private Byte wc;

	private Byte tv;

	private Byte bar;

	public Integer getRoomCategoryId() {
		return roomCategoryId;
	}

	public void setRoomCategoryId(Integer roomCategoryId) {
		this.roomCategoryId = roomCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Byte getWifi() {
		return wifi;
	}

	public void setWifi(Byte wifi) {
		this.wifi = wifi;
	}

	public Byte getWc() {
		return wc;
	}

	public void setWc(Byte wc) {
		this.wc = wc;
	}

	public Byte getTv() {
		return tv;
	}

	public void setTv(Byte tv) {
		this.tv = tv;
	}

	public Byte getBar() {
		return bar;
	}

	public void setBar(Byte bar) {
		this.bar = bar;
	}

	
	
	

}
