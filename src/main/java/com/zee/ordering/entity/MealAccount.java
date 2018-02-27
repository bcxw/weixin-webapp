package com.zee.ordering.entity;

import java.util.Date;

import com.zee.ordering.common.BaseEntity;

public class MealAccount extends BaseEntity {
	private long userId;
	private String userName;
	private String weixinid;
	private int restaurantId;
	private int departmentId;
	private String gender;
	private Date mealDate;
	private String mealWeek;
	private int breakfast;
	private int lunch;
	private int dinner;
	
	private int breakfastClock;
	private int lunchClock;
	private int dinnerClock;
	private String clockDetail;
	private String clockName;
	private String clockCode;
	
	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWeixinid() {
		return weixinid;
	}

	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getMealDate() {
		return mealDate;
	}

	public void setMealDate(Date mealDate) {
		this.mealDate = mealDate;
	}

	public String getMealWeek() {
		return mealWeek;
	}

	public void setMealWeek(String mealWeek) {
		this.mealWeek = mealWeek;
	}

	public int getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(int breakfast) {
		this.breakfast = breakfast;
	}

	public int getLunch() {
		return lunch;
	}

	public void setLunch(int lunch) {
		this.lunch = lunch;
	}

	public int getDinner() {
		return dinner;
	}

	public void setDinner(int dinner) {
		this.dinner = dinner;
	}

	public int getBreakfastClock() {
		return breakfastClock;
	}

	public void setBreakfastClock(int breakfastClock) {
		this.breakfastClock = breakfastClock;
	}

	public int getLunchClock() {
		return lunchClock;
	}

	public void setLunchClock(int lunchClock) {
		this.lunchClock = lunchClock;
	}

	public int getDinnerClock() {
		return dinnerClock;
	}

	public void setDinnerClock(int dinnerClock) {
		this.dinnerClock = dinnerClock;
	}

	public String getClockDetail() {
		return clockDetail;
	}

	public void setClockDetail(String clockDetail) {
		this.clockDetail = clockDetail;
	}

	public String getClockName() {
		return clockName;
	}

	public void setClockName(String clockName) {
		this.clockName = clockName;
	}

	public String getClockCode() {
		return clockCode;
	}

	public void setClockCode(String clockCode) {
		this.clockCode = clockCode;
	}
	
	
}
