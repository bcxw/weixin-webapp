package com.zee.ordering.entity;

import java.util.Date;

import com.zee.ordering.common.BaseEntity;

public class ClockHistory extends BaseEntity {
	private Date clockDate;
	private String clockCode;
	private String clockName;
	private String clockDetail;

	public Date getClockDate() {
		return clockDate;
	}

	public void setClockDate(Date clockDate) {
		this.clockDate = clockDate;
	}

	public String getClockCode() {
		return clockCode;
	}

	public void setClockCode(String clockCode) {
		this.clockCode = clockCode;
	}

	public String getClockName() {
		return clockName;
	}

	public void setClockName(String clockName) {
		this.clockName = clockName;
	}

	public String getClockDetail() {
		return clockDetail;
	}

	public void setClockDetail(String clockDetail) {
		this.clockDetail = clockDetail;
	}

}
