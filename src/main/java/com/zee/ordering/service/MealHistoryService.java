package com.zee.ordering.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MealHistoryService {

	/**
	 * 获取今天和明天报餐汇总情况
	 * 
	 * @return {breakfast1:0,lunch1:0,dinner1:0,breakfast2:0,lunch2:0,dinner2:0}
	 */
	public Map<String, Object> getUserMealHistory(String wxUserId,Date mealDateOne,Date mealDateTwo);

	/**
	 * 获取今天和明天个人报餐汇总情况
	 * 
	 * @param wxUserId
	 *            微信端用户id
	 * @return {breakfast1:0,lunch1:0,dinner1:0,breakfast2:0,lunch2:0,dinner2:0}
	 */
	public Map<String, Object> countAllUserMealHistory(Date mealDateOne,Date mealDateTwo,int restaurantId);

	public int updateMealHistory(@Param("breakfast")int breakfast,@Param("lunch")int lunch,@Param("dinner")int dinner,@Param("wxUserId")String wxUserId,@Param("mealDate")Date mealDate);
	
	public List<Map<String,Object>> getUserMealHistoryAll(String wxUserId);
	
	public List<Map<String,Object>> getCurrentMealHistory(String restaurantId,Date mealDate);
}
