package com.zee.ordering.service;

import java.util.Map;

public interface MealDefaultService {

	public Map<String, Object> getUserMealDefalut(String wxUserId);

	public void modifyMealDefault(String wxUserId, String week0, String week1, String week2, String week3, String week4,
			String week5, String week6);
}
