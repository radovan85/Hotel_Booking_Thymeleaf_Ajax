package com.radovan.spring.dto;

import java.io.Serializable;

public class RoomCategoryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
