package com.zee.ordering.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigContext {
	@Value("${breakfastTerminalTime}")
	private String breakfastTerminalTime;

	@Value("${lunchTerminalTime}")
	private String lunchTerminalTime;

	@Value("${dinnerTerminalTime}")
	private String dinnerTerminalTime;
	
	@Value("${donationMoney}")
	private String donationMoney;
	
	@Value("${leaveDepartmentId}")
	private String leaveDepartmentId;
	
	@Value("${breakfastClockStart}")
	private String breakfastClockStart;
	
	@Value("${breakfastClockEnd}")
	private String breakfastClockEnd;
	
	@Value("${lunchClockStart}")
	private String lunchClockStart;
	
	@Value("${lunchClockEnd}")
	private String lunchClockEnd;
	
	@Value("${dinnerClockStart}")
	private String dinnerClockStart;
	
	@Value("${dinnerClockEnd}")
	private String dinnerClockEnd;
	
	
	public String getBreakfastTerminalTime() {
		return breakfastTerminalTime;
	}

	public void setBreakfastTerminalTime(String breakfastTerminalTime) {
		this.breakfastTerminalTime = breakfastTerminalTime;
	}

	public String getLunchTerminalTime() {
		return lunchTerminalTime;
	}

	public void setLunchTerminalTime(String lunchTerminalTime) {
		this.lunchTerminalTime = lunchTerminalTime;
	}

	public String getDinnerTerminalTime() {
		return dinnerTerminalTime;
	}

	public void setDinnerTerminalTime(String dinnerTerminalTime) {
		this.dinnerTerminalTime = dinnerTerminalTime;
	}

	public String getDonationMoney() {
		return donationMoney;
	}

	public void setDonationMoney(String donationMoney) {
		this.donationMoney = donationMoney;
	}

	public String getLeaveDepartmentId() {
		return leaveDepartmentId;
	}

	public void setLeaveDepartmentId(String leaveDepartmentId) {
		this.leaveDepartmentId = leaveDepartmentId;
	}

	public String getBreakfastClockStart() {
		return breakfastClockStart;
	}

	public void setBreakfastClockStart(String breakfastClockStart) {
		this.breakfastClockStart = breakfastClockStart;
	}

	public String getBreakfastClockEnd() {
		return breakfastClockEnd;
	}

	public void setBreakfastClockEnd(String breakfastClockEnd) {
		this.breakfastClockEnd = breakfastClockEnd;
	}

	public String getLunchClockStart() {
		return lunchClockStart;
	}

	public void setLunchClockStart(String lunchClockStart) {
		this.lunchClockStart = lunchClockStart;
	}

	public String getLunchClockEnd() {
		return lunchClockEnd;
	}

	public void setLunchClockEnd(String lunchClockEnd) {
		this.lunchClockEnd = lunchClockEnd;
	}

	public String getDinnerClockStart() {
		return dinnerClockStart;
	}

	public void setDinnerClockStart(String dinnerClockStart) {
		this.dinnerClockStart = dinnerClockStart;
	}

	public String getDinnerClockEnd() {
		return dinnerClockEnd;
	}

	public void setDinnerClockEnd(String dinnerClockEnd) {
		this.dinnerClockEnd = dinnerClockEnd;
	}
	
}
