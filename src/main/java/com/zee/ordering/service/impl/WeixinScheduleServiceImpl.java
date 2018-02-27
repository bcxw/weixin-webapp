package com.zee.ordering.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zee.ordering.common.WeixinUtil;
import com.zee.ordering.context.ConfigContext;
import com.zee.ordering.dao.DepartmentDao;
import com.zee.ordering.dao.MealHistoryDao;
import com.zee.ordering.dao.RestanurantDao;
import com.zee.ordering.dao.UserDao;
import com.zee.ordering.entity.Department;
import com.zee.ordering.entity.MealHistory;
import com.zee.ordering.entity.User;
import com.zee.ordering.service.WeixinScheduleService;

@Service
public class WeixinScheduleServiceImpl implements WeixinScheduleService {
	private static final Logger logger = LoggerFactory.getLogger(WeixinScheduleServiceImpl.class);

	@Resource
	private UserDao userDao;

	@Resource
	private MealHistoryDao mealHistoryDao;

	@Resource
	private ConfigContext configContext;

	@Resource
	private RestanurantDao restanurantDao;
	
	@Resource
	private DepartmentDao departmentDao;
	

	@Override
	@Scheduled(cron = "0 50 0-23 * * ?") // 每个整点执行
	public void sendOrderOverMessage() {

		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		// 整点执行提醒一次
		if (hour == (Integer.parseInt(configContext.getLunchTerminalTime()) - 1)) {// 中餐结束提醒
			String ards = restanurantDao.getAllResturantDepartmentIds();
			String[] ardsArr = ards.split(",");
			ards = StringUtils.join(ardsArr, "|");

			String content = "午餐即将在" + configContext.getLunchTerminalTime() + ":00结束报餐";
			WeixinUtil.sendTextMessage("", ards, WeixinUtil.DIANCAN_APPID, content);
		} else if (hour == (Integer.parseInt(configContext.getDinnerTerminalTime()) - 1)) {// 晚餐结束提醒
			String ards = restanurantDao.getAllResturantDepartmentIds();
			String[] ardsArr = ards.split(",");
			ards = StringUtils.join(ardsArr, "|");

			String content = "晚餐即将在" + configContext.getDinnerTerminalTime() + ":00结束报餐";
			WeixinUtil.sendTextMessage("", ards, WeixinUtil.DIANCAN_APPID, content);
		} else if (hour == (Integer.parseInt(configContext.getBreakfastTerminalTime()) - 1)) {// 早餐结束提醒
			String ards = restanurantDao.getAllResturantDepartmentIds();
			String[] ardsArr = ards.split(",");
			ards = StringUtils.join(ardsArr, "|");

			String content = "早餐即将在" + configContext.getBreakfastTerminalTime() + ":00结束报餐";
			WeixinUtil.sendTextMessage("", ards, WeixinUtil.DIANCAN_APPID, content);
		}

	}

	@Override
	public void addMealHistory() {

		List<MealHistory> mealHistoryList = new ArrayList<MealHistory>();

		List<Map<String, Object>> allUserInfo = userDao.findAllValidUser();
		for (int i = 0; i < allUserInfo.size(); i++) {
			Map<String, Object> oneUserInfo = allUserInfo.get(i);

			// 人员部门要对应餐厅才可以就餐,才有就餐记录
			if (oneUserInfo != null && oneUserInfo.get("id") != null && oneUserInfo.get("restaurant_id") != null
					&& Integer.parseInt(oneUserInfo.get("restaurant_id") + "") >= 0) {

				long userId = Long.parseLong(oneUserInfo.get("id") + "");
				String userName = oneUserInfo.get("wx_name") + "";
				String weixinid = oneUserInfo.get("wx_user_id") + "";
				int restaurantId = oneUserInfo.get("restaurant_id") != null
						? Integer.parseInt(oneUserInfo.get("restaurant_id") + "") : -1;
				int departmentId = oneUserInfo.get("department_id") != null
						? Integer.parseInt(oneUserInfo.get("department_id") + "") : -1;
				String gender = oneUserInfo.get("wx_gender") + "";

				Date nowDate = new Date();
				Calendar calendar = Calendar.getInstance();
				// 同步2天
				for (int d = 0; d <= 1; d++) {
					calendar.setTime(nowDate);
					calendar.add(calendar.DATE, d);

					if (mealHistoryDao.existHistory(oneUserInfo.get("wx_user_id") + "", calendar.getTime()) <= 0) {
						MealHistory mealHistory = new MealHistory();
						mealHistory.setUserId(userId);
						mealHistory.setUserName(userName);
						mealHistory.setWeixinid(weixinid);
						mealHistory.setRestaurantId(restaurantId);
						mealHistory.setDepartmentId(departmentId);
						mealHistory.setGender(gender);

						mealHistory.setMealDate(calendar.getTime());

						int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
						week_index = week_index < 0 ? 0 : week_index;

						mealHistory.setMealWeek(week_index + "");

						String mealStr = oneUserInfo.get("week" + week_index) != null
								? oneUserInfo.get("week" + week_index) + "" : "";
						// 默认都是点餐的，没有值就有默认值
						mealStr = StringUtils.isBlank(mealStr) ? "1,1,1" : mealStr;

						String[] dayMealArr = mealStr.split(",");

						// 同样缺少值也是默认点了餐的
						int breakfast = dayMealArr.length >= 1 && StringUtils.isNotBlank(dayMealArr[0])
								? Integer.parseInt(dayMealArr[0]) : 1;
						int lunch = dayMealArr.length >= 2 && StringUtils.isNotBlank(dayMealArr[1])
								? Integer.parseInt(dayMealArr[1]) : 1;
						int dinner = dayMealArr.length >= 3 && StringUtils.isNotBlank(dayMealArr[2])
								? Integer.parseInt(dayMealArr[2]) : 1;

						mealHistory.setBreakfast(breakfast);
						mealHistory.setLunch(lunch);
						mealHistory.setDinner(dinner);

						mealHistoryList.add(mealHistory);

					}
				}

			}

		}
		if (mealHistoryList.size() > 0) {
			int num = mealHistoryDao.save(mealHistoryList);

			// 日志
			logger.info("自动同步成功导入到mealhistory数据条数：" + num);
		}
	}

	@Override
	@Scheduled(cron = "0 0/30 * * * ? ") // 3十分钟执行一次,最重要的信息同步，单独放在一起
	public void syncUserInfo() {
		// 每个整点要同步一下人员数据，防止人员信息错误
		List<Map<String, Object>> userList = WeixinUtil.getAllUserList();
		for (int i = 0; i < userList.size(); i++) {
			Map<String, Object> userMap = userList.get(i);
			String userid = userMap.get("userid") + "";

			User user = userDao.findUser(userid);
			if (user != null) {

				user.setWxName(userMap.get("name") + "");
				user.setWxGender(userMap.get("gender") + "");
				user.setWxWeixinid(userMap.get("weixinid") + "");
				user.setWxAvatar(userMap.get("avatar") + "");
				user.setWxStatus(userMap.get("status") + "");

				List<Long> departmets = (List<Long>) userMap.get("department");

				userDao.update(user);
				long id = user.getId();
				userDao.deleteDepartments(id);
				userDao.saveDepartments(id, departmets);

				// 日志
				logger.info("自动同步更新用户：" + userMap.get("name"));
			} else {
				User userNew = new User();
				userNew.setWxUserId(userMap.get("userid") + "");
				userNew.setWxName(userMap.get("name") + "");
				userNew.setWxGender(userMap.get("gender") + "");
				userNew.setWxWeixinid(userMap.get("weixinid") + "");
				userNew.setWxAvatar(userMap.get("avatar") + "");
				userNew.setWxStatus(userMap.get("status") + "");

				List<Long> departmets = (List<Long>) userMap.get("department");

				userDao.save(userNew);
				long id = userNew.getId();
				userDao.saveDepartments(id, departmets);

				// 日志
				logger.info("自动同步添加用户：" + userMap.get("name"));
			}
		}

		// 每个整点要根据默认情况生成报餐记录，生成未来3天的，已生成的绝对不能修改生成
		this.addMealHistory();

	}

	@Override
	@Scheduled(cron = "0 10 0-23 * * ?") // 每个整点过十分执行
	public void syncDepartment() {

		List<Map<String, Object>> departmentList = WeixinUtil.getDepartmentList();

		for (int i = 0; i < departmentList.size(); i++) {
			Map<String, Object> dep = departmentList.get(i);

			long id = Long.parseLong(dep.get("id") + "");
			String name = dep.get("name") + "";
			int parentId = Integer.parseInt(dep.get("parentid") + "");
			int order = Integer.parseInt(dep.get("order") + "");

			Department newDep=new Department();
			newDep.setId(id);
			newDep.setName(name);
			newDep.setParentId(parentId);
			newDep.setOrder(order);
			
			if(departmentDao.existDepartment(newDep)>0){
				departmentDao.update(newDep);
			}else{
				departmentDao.insert(newDep);
			}
			
		}

	}

}
