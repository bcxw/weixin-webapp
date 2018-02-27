package com.zee.ordering.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zee.ordering.entity.MealAccount;

public interface MealAccountDao {
	
	public int existData(@Param("mealMonth") String mealMonth);
	
	public int save(List<MealAccount> clockHistoryList);
	
	public List<MealAccount> getAllMealAccount(@Param("month")String month,@Param("name")String name,@Param("page")int page, @Param("size")int size);
	
	public int getAllMealAccountTotal(@Param("month")String month,@Param("name")String name);
	
	public List<MealAccount> getMealAccountByMonth(@Param("mealMonth") String mealMonth);
}
