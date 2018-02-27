package com.zee.ordering.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zee.ordering.entity.MealHistory;

public interface MealHistoryDao {

	/**
	 * 判断是否存在报餐记录
	 * 
	 * @param userId
	 *            用户的微信上的userid
	 * @param mealDate
	 *            进餐日期
	 * @return 记录条数
	 */
	public int existHistory(@Param("userId") String userId, @Param("mealDate") Date mealDate);

	/**
	 * 保存历史记录
	 * @param mealHistoryList 记录
	 * @return num
	 */
	public int save(@Param("mealHistoryList")List<MealHistory> mealHistoryList);
	
	public List<Map<String,Object>> getUserMealHistory(@Param("wxUserId")String wxUserId,@Param("mealDate")Date mealDate);
	
	public List<Map<String,Object>> countAllUserMealHistory(@Param("mealDate")Date mealDate ,@Param("restaurantId")int restaurantId  );
	
	public int updateMealHistory(@Param("breakfast")int breakfast,@Param("lunch")int lunch,@Param("dinner")int dinner,@Param("wxUserId")String wxUserId,@Param("mealDate")Date mealDate);
	
	public List<Map<String,Object>> getUserMealHistoryAll(@Param("wxUserId")String wxUserId);
	
	public List<Map<String,Object>> getCurrentMealHistory(@Param("restaurantId")String restaurantId,@Param("mealDate")Date mealDate);

	/**
	 * 查询某一月的就餐记录
	 * @param month
	 * @return
	 */
	public List<MealHistory> getMealHistoryByMonth(@Param("month")String month);
	
	public void deleteByWeixinIdDate(@Param("weixinId")String weixinId,@Param("mealDate")Date mealDate);
}
