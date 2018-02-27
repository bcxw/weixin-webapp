package com.zee.ordering.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zee.ordering.common.AjaxResult;
import com.zee.ordering.entity.MealAccount;

public interface MealManageService {
	
	/**
	 * 导入打卡记录
	 * @param inputStream
	 * @return
	 */
	public AjaxResult importClockHistory(InputStream inputStream);
	
	/**
	 * 打卡就餐结算
	 * @param clockMonth 结算的月份
	 * @param empList 所有匹配好的员工列表
	 * 		weixin_id:weixin_id,
			weixin_name:weixin_name,
			clock_id:clock_id,
			clock_name:clock_name,
			match_status:match_status
	 * @return
	 */
	public AjaxResult mealClockAccount(String clockMonth,List<Map<String, Object>> empList);
	
	/**
	 * 总数
	 * @return
	 */
	public int getAllMealAccountTotal(String month,String name);
	
	/**
	 * 分页记录
	 * @param page
	 * @param size
	 * @return
	 */
	public List<MealAccount> getAllMealAccount(String month,String name,int page,int size);
	
	/**
	 * 导出excel表格
	 * @param month
	 * @return
	 */
	public HSSFWorkbook exportMealAccount(String month);
	
}
