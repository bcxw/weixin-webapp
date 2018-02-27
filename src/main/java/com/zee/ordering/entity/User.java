package com.zee.ordering.entity;

import com.zee.ordering.common.BaseEntity;

public class User extends BaseEntity {
	private String wxUserId;
	private String wxName;
	private String wxGender;
	private String wxWeixinid;
	private String wxAvatar;
	private String wxStatus;

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

}
