package com.zee.ordering.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zee.ordering.dao.MealDefaultDao;
import com.zee.ordering.dao.UserDao;
import com.zee.ordering.entity.MealDefault;
import com.zee.ordering.service.MealDefaultService;

@Service
public class MealDefaultServiceImpl implements MealDefaultService {
	
	@Resource
	private MealDefaultDao mealDefaultDao;
	
	@Resource
	private UserDao userDao;

	@Override
	public Map<String, Object> getUserMealDefalut(String wxUserId) {
		Map<String, Object> returnMap=new HashMap<String, Object>();
		List<Map<String, Object>> list =mealDefaultDao.getUserMealDefalut(wxUserId);
		if(list!=null&&!list.isEmpty()){
			returnMap=list.get(0);
		}
		return returnMap;
	}

	@Override
	public void modifyMealDefault(String wxUserId, String week0, String week1, String week2, String week3, String week4,
			String week5, String week6) {
		MealDefault mealDefault=new MealDefault();
		mealDefault.setWxUserId(wxUserId);
		mealDefault.setWeek0(week0);
		mealDefault.setWeek1(week1);
		mealDefault.setWeek2(week2);
		mealDefault.setWeek3(week3);
		mealDefault.setWeek4(week4);
		mealDefault.setWeek5(week5);
		mealDefault.setWeek6(week6);
		
		List<Map<String,Object>> userList=userDao.getUserDetail(wxUserId);
		if(userList!=null){
			Map<String,Object> userMap=userList.get(0);
			mealDefault.setUserId(Integer.parseInt(userMap.get("id")+""));
		}
		
		List<Map<String,Object>> mealDefaultList=mealDefaultDao.getUserMealDefalut(wxUserId);
		if(mealDefaultList!=null&&!mealDefaultList.isEmpty()){
			mealDefaultDao.update(mealDefault);
		}else{
			
			mealDefaultDao.insert(mealDefault);
		}
	}
	
	

	

}
