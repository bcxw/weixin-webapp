package com.zee.ordering.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zee.ordering.entity.MealDefault;

public interface MealDefaultDao {
	
	public List<Map<String,Object>> getUserMealDefalut(@Param("wxUserId")String wxUserId);
	
	public int update(MealDefault mealDefault);
	
	public int insert(MealDefault mealDefault);
	
}
