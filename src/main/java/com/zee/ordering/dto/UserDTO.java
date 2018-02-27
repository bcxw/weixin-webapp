package com.zee.ordering.dto;

public class UserDTO {

	private String wxUserId;
	private String wxName;
	private String wxDepartment;
	private String wxGender;
	private String wxWeixinid;
	private String wxAvatar;
	private String wxStatus;
	private String restaurantId;

	public String getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}

	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

	public String getWxDepartment() {
		return wxDepartment;
	}

	public void setWxDepartment(String wxDepartment) {
		this.wxDepartment = wxDepartment;
	}

	public String getWxGender() {
		return wxGender;
	}

	public void setWxGender(String wxGender) {
		this.wxGender = wxGender;
	}

	public String getWxWeixinid() {
		return wxWeixinid;
	}

	public void setWxWeixinid(String wxWeixinid) {
		this.wxWeixinid = wxWeixinid;
	}

	public String getWxAvatar() {
		return wxAvatar;
	}

	public void setWxAvatar(String wxAvatar) {
		this.wxAvatar = wxAvatar;
	}

	public String getWxStatus() {
		return wxStatus;
	}

	public void setWxStatus(String wxStatus) {
		this.wxStatus = wxStatus;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

}
