package com.zee.ordering.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 微信定时任务
 * @author admin
 *
 */
public interface WeixinScheduleService {
	
	//定时发送订餐结束信息提示给餐饮部
	public void sendOrderOverMessage();
	
	/**
	 * 把人员信息添加到就餐记录
	 * @param userInfo 用户信息
	 * @param date 日期
	 */
	public void addMealHistory();
	
	/**
	 * 同步用户信息
	 */
	public void syncUserInfo();
	
	/**
	 * 同步部门信息
	 */
	public void syncDepartment();
}
