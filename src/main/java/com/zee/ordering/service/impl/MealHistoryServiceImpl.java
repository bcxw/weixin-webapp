package com.zee.ordering.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zee.ordering.dao.MealHistoryDao;
import com.zee.ordering.dao.UserDao;
import com.zee.ordering.entity.MealHistory;
import com.zee.ordering.service.MealHistoryService;

@Service
public class MealHistoryServiceImpl implements MealHistoryService {
	
	@Resource
	private MealHistoryDao mealHistoryDao;
	
	@Resource
	private UserDao userDao;

	@Override
	public Map<String, Object> getUserMealHistory(String wxUserId,Date mealDateOne,Date mealDateTwo) {
		Map<String, Object> returnMap=new HashMap<String, Object>();
		List<Map<String, Object>> list=mealHistoryDao.getUserMealHistory(wxUserId, mealDateOne);
		
		if(list==null||list.isEmpty()){
			returnMap.put("breakfast1", 1);
			returnMap.put("lunch1", 1);
			returnMap.put("dinner1", 1);
		}else{
			Map<String, Object> oneData=list.get(0);
			returnMap.put("breakfast1", oneData.get("breakfast"));
			returnMap.put("lunch1", oneData.get("lunch"));
			returnMap.put("dinner1", oneData.get("dinner"));
		}
		
		list=mealHistoryDao.getUserMealHistory(wxUserId, mealDateTwo);
		
		if(list==null||list.isEmpty()){
			returnMap.put("breakfast2", 1);
			returnMap.put("lunch2", 1);
			returnMap.put("dinner2", 1);
		}else{
			Map<String, Object> oneData=list.get(0);
			returnMap.put("breakfast2", oneData.get("breakfast"));
			returnMap.put("lunch2", oneData.get("lunch"));
			returnMap.put("dinner2", oneData.get("dinner"));
		}
		
		return returnMap;
	}

	@Override
	public Map<String, Object> countAllUserMealHistory(Date mealDateOne,Date mealDateTwo,int restaurantId) {
		Map<String, Object> returnMap=new HashMap<String, Object>();
		List<Map<String, Object>> list=mealHistoryDao.countAllUserMealHistory(mealDateOne, restaurantId);
		Map<String, Object> oneData=list!=null&&!list.isEmpty()?list.get(0):new HashMap<String, Object>();
		returnMap.put("breakfast1", oneData.get("breakfast"));
		returnMap.put("lunch1", oneData.get("lunch"));
		returnMap.put("dinner1", oneData.get("dinner"));
		list=mealHistoryDao.countAllUserMealHistory( mealDateTwo, restaurantId);
		oneData=list!=null&&!list.isEmpty()?list.get(0):new HashMap<String, Object>();
		returnMap.put("breakfast2", oneData.get("breakfast"));
		returnMap.put("lunch2", oneData.get("lunch"));
		returnMap.put("dinner2", oneData.get("dinner"));
		return returnMap;
	}

	@Override
	public int updateMealHistory(int breakfast, int lunch, int dinner, String wxUserId, Date mealDate) {
		int num=0;
		if(mealHistoryDao.existHistory(wxUserId, mealDate)>0){
			num = mealHistoryDao.updateMealHistory(breakfast, lunch, dinner, wxUserId, mealDate);
		}else{
			List<MealHistory> mealHistoryList =new ArrayList<MealHistory>();
			
			List<Map<String,Object>> allUserInfo=userDao.getUserDetail(wxUserId);
			Map<String,Object> oneMap=allUserInfo.get(0);
			
			//人员部门要对应餐厅才可以就餐,才有就餐记录
		    	
			long userId=Long.parseLong(oneMap.get("id")+"");
			String userName=oneMap.get("wx_name")+"";
			String weixinid=oneMap.get("wx_user_id")+"";
			int restaurantId=oneMap.get("restaurant_id")!=null?Integer.parseInt(oneMap.get("restaurant_id")+""):-1;
			int departmentId=oneMap.get("department_id")!=null?Integer.parseInt(oneMap.get("department_id")+""):-1;
			String gender=oneMap.get("wx_gender")+"";
			
			
			MealHistory mealHistory=new MealHistory();
			mealHistory.setUserId(userId);
			mealHistory.setUserName(userName);
			mealHistory.setWeixinid(weixinid);
			mealHistory.setRestaurantId(restaurantId);
			mealHistory.setDepartmentId(departmentId);
			mealHistory.setGender(gender);
			mealHistory.setMealDate(mealDate);
			
			Calendar  calendar   =   Calendar.getInstance();
			calendar.setTime(mealDate);
			
			int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			week_index=week_index<0?0:week_index;
			
			mealHistory.setMealWeek(week_index+"");
			
			mealHistory.setBreakfast(breakfast);
			mealHistory.setLunch(lunch);
			mealHistory.setDinner(dinner);
			
			mealHistoryList.add(mealHistory);
			num=mealHistoryDao.save(mealHistoryList);
		}
		
		return num;
	}

	@Override
	public List<Map<String, Object>> getUserMealHistoryAll(String wxUserId) {
		return mealHistoryDao.getUserMealHistoryAll(wxUserId);
	}

	@Override
	public List<Map<String, Object>> getCurrentMealHistory(String restaurantId, Date mealDate) {
		return mealHistoryDao.getCurrentMealHistory(restaurantId, mealDate);
	}

	

}
