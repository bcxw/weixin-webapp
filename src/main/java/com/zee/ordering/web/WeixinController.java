package com.zee.ordering.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zee.ordering.common.WeixinUtil;
import com.zee.ordering.context.ConfigContext;
import com.zee.ordering.entity.User;
import com.zee.ordering.service.MealDefaultService;
import com.zee.ordering.service.MealHistoryService;
import com.zee.ordering.service.UserService;
import com.zee.ordering.service.WeixinScheduleService;

@Controller
@RequestMapping("/weixin")
public class WeixinController {

	private static final Logger logger = LoggerFactory.getLogger(WeixinController.class);

	@Resource
	private UserService userService;

	@Resource
	private ConfigContext configContext;

	@Resource
	private MealHistoryService mealHistoryService;
	
	@Resource
	private MealDefaultService mealDefaultService;
	
	@Resource
	private WeixinScheduleService weixinScheduleService;
	
	

	@RequestMapping("/test")
	public String test(Model model) {
		model.addAttribute("breakfastTerminalTime", configContext.getBreakfastTerminalTime());
		model.addAttribute("lunchTerminalTime", configContext.getLunchTerminalTime());
		model.addAttribute("dinnerTerminalTime", configContext.getDinnerTerminalTime());
		return "diancanApp/index";
	}
	
	@RequestMapping("/startMeal")
	public String startMeal(@CookieValue(value = "wxUserId", required = false) String wxUserId,Model model) {
		Date nowDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(calendar.DATE, 1);
		model.addAttribute("userMealInfo",
				mealHistoryService.getUserMealHistory(wxUserId, nowDate, calendar.getTime()));
		model.addAttribute("breakfastTerminalTime", configContext.getBreakfastTerminalTime());
		model.addAttribute("lunchTerminalTime", configContext.getLunchTerminalTime());
		model.addAttribute("dinnerTerminalTime", configContext.getDinnerTerminalTime());
		return "diancanApp/startMeal";
	}
	
	@RequestMapping("/advinceMeal")
	public String advinceMeal(@CookieValue(value = "wxUserId", required = false) String wxUserId,Model model) {
		model.addAttribute("mealDefaultInfo", mealDefaultService.getUserMealDefalut(wxUserId));
		return "diancanApp/advinceMeal";
	}
	
	@RequestMapping("/currentMealRecord")
	public String currentMealRecord(@CookieValue(value = "wxUserId", required = false) String wxUserId,String meal,String mealDate,Model model) {
		Map<String, Object> userDetail = userService.getUserDetail(wxUserId);
		String restaurantId=userDetail!=null&&userDetail.get("restaurant_id")!=null?userDetail.get("restaurant_id")+"":"-1";
		
		SimpleDateFormat formatterAll = new SimpleDateFormat ("yyyy-MM-dd");
		Date date;
		try {
			date = formatterAll.parse(mealDate);
			model.addAttribute("mealDate", mealDate);
			model.addAttribute("meal", meal);
			model.addAttribute("mealInfo", mealHistoryService.getCurrentMealHistory(restaurantId, date));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return "diancanApp/currentMealRecord";
	}
	
	@RequestMapping("/historyRecord")
	public String historyRecord(@CookieValue(value = "wxUserId", required = false) String wxUserId,Model model) {
		model.addAttribute("userMealHistory", mealHistoryService.getUserMealHistoryAll(wxUserId));
		return "diancanApp/historyRecord";
	}

	/**
	 * 微信点餐主接口，用于微信接口配置
	 */
	@RequestMapping("/diancanIndex")
	public String diancanIndex(@CookieValue(value = "wxUserId", required = false) String wxUserId, String code,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			// 判断有没有cookie,有的话直接跳转到主页面,没有cookie则去取到用户了再写入cookie,然后跳转到主页面
			if (("null".equals(wxUserId)||StringUtils.isEmpty(wxUserId)) && StringUtils.isEmpty(code)) {
				logger.info("用户进入系统没有cookie准备去取code");
				String redirectUrl = request.getRequestURL().toString();
				redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
				return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeixinUtil.CORPID
						+ "&redirect_uri=" + redirectUrl + "&response_type=code&scope=snsapi_userinfo&agentid="
						+ WeixinUtil.DIANCAN_APPID + "&state=zeediancanrenyuan#wechat_redirect";
			} else {
				// 微信端的userid
				String userId = "";
				if (StringUtils.isNotEmpty(code)) {
					logger.info("用户进入系统没有cookie已取code,获取用户信息创建cookie后进入系统");
					// 根据code获取用户信息
					Map<String, Object> userInfo = WeixinUtil.getUserInfoByCode(code);
					
					if(userInfo.get("UserId")!=null){
						userId = userInfo.get("UserId") + "";

						// 创建cookie
						Cookie cookie = new Cookie("wxUserId", userId);
						cookie.setMaxAge(60 * 60 * 24 * 100);
						response.addCookie(cookie);
					}else{
						return "diancanApp/index";
					}
					
				} else {
					userId = wxUserId;
					logger.info("用户进入系统有cookie-" + userId);
				}

				User user = userService.findUser(userId);
				// 没有用户就同步创建用户
				if (user == null) {
					Map<String, Object> userInfo = WeixinUtil.getUserInfo(userId);
					user = new User();
					user.setWxUserId(userId);
					user.setWxName(userInfo.get("name") + "");
					user.setWxGender(userInfo.get("gender") + "");
					user.setWxWeixinid(userInfo.get("weixinid") + "");
					user.setWxAvatar(userInfo.get("avatar") + "");
					user.setWxStatus(userInfo.get("status") + "");
					List<Long> departmets = (List<Long>) userInfo.get("department");
					userService.save(user, departmets);
				}

				// 查询报餐数据给到页面

				Map<String, Object> userDetail = userService.getUserDetail(userId);
				model.addAttribute("restaurantName", userDetail.get("restaurant_name"));
				model.addAttribute("userId", userId);
				model.addAttribute("userName", user.getWxName()+"("+userId+")");

				model.addAttribute("breakfastTerminalTime", configContext.getBreakfastTerminalTime());
				model.addAttribute("lunchTerminalTime", configContext.getLunchTerminalTime());
				model.addAttribute("dinnerTerminalTime", configContext.getDinnerTerminalTime());

				Date nowDate = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(nowDate);
				calendar.add(calendar.DATE, 1);

				model.addAttribute("userMealInfo",
						mealHistoryService.getUserMealHistory(userId, nowDate, calendar.getTime()));
				int restaurantId=userDetail.get("restaurant_id")!=null?Integer.parseInt(userDetail.get("restaurant_id")+""):-1;
				model.addAttribute("countMealInfo",
						mealHistoryService.countAllUserMealHistory(nowDate, calendar.getTime(),restaurantId));

				return "diancanApp/index";
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			return "diancanApp/error";
		}

	}

	/**
	 * 接受微信消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/acceptWeixin")
	@ResponseBody
	public void acceptWeixin(HttpServletRequest request, HttpServletResponse response) {

		try {
			Map<String, String> acceptMessageMap = WeixinUtil.parseXml(request);

			String event = acceptMessageMap.get("Event");
			if ("subscribe".equals(event)) {
				// 关注成功要发提示信息 并建一条人员数据
				String userId = acceptMessageMap.get("FromUserName");
				WeixinUtil.sendTextMessage(userId, "", WeixinUtil.QIYE_APPID,
						"温馨提示：报餐系统上午"+configContext.getLunchTerminalTime()+"点结束中餐报餐，下午"+configContext.getDinnerTerminalTime()+"点结束晚餐报餐，下午"+configContext.getBreakfastTerminalTime()+"点结束第二天早上的报餐。");

				User user = userService.findUser(userId);

				Map<String, Object> userMap = WeixinUtil.getUserInfo(userId);

				if (user != null) {

					user.setWxName(userMap.get("name") + "");
					user.setWxGender(userMap.get("gender") + "");
					user.setWxWeixinid(userMap.get("weixinid") + "");
					user.setWxAvatar(userMap.get("avatar") + "");
					user.setWxStatus(userMap.get("status") + "");
					List<Long> departmets = (List<Long>) userMap.get("department");
					userService.update(user, departmets);
				} else {
					User userNew = new User();
					userNew.setWxUserId(userMap.get("userid") + "");
					userNew.setWxName(userMap.get("name") + "");
					userNew.setWxGender(userMap.get("gender") + "");
					userNew.setWxWeixinid(userMap.get("weixinid") + "");
					userNew.setWxAvatar(userMap.get("avatar") + "");
					userNew.setWxStatus(userMap.get("status") + "");

					List<Long> departmets = (List<Long>) userMap.get("department");
					userService.save(userNew, departmets);
				}
				logger.info("关注企业号成功" + userMap.get("name"));
			} else if ("unsubscribe".equals(event)) {
				// 取消关注 好像不能干什么
//				String userId = acceptMessageMap.get("FromUserName");
//				Map<String, Object> userMap = WeixinUtil.getUserInfo(userId);
//				User user = userService.findUser(userId);
//				user.setWxStatus(userMap.get("status") + "");
//				List<Long> departmets = new ArrayList<Long>();
//				userService.update(user, departmets);
				logger.info("取消关注企业号成功" + acceptMessageMap.toString());
			} else if (StringUtils.isBlank(event) && "text".equals(acceptMessageMap.get("MsgType"))) {
				logger.info("接受到文本消息-" + acceptMessageMap.get("FromUserName") + ":" + acceptMessageMap.get("Content"));
			}

			String enchoStr = WeixinUtil.verifyURL(request);

			PrintWriter writer = response.getWriter();
			writer.print(enchoStr);
			writer.close();
			logger.info("微信返回验证文本" + enchoStr);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}
	
	@RequestMapping(value="/modifyMeal")
	@ResponseBody
	public Map<String,Object> modifyMeal(@CookieValue(value = "wxUserId", required = false) String wxUserId,int mybreakfast1,int mylunch1,int mydinner1,int mybreakfast2,int mylunch2,int mydinner2) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		Date nowDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(calendar.DATE, 1);
		mealHistoryService.updateMealHistory(mybreakfast1, mylunch1, mydinner1, wxUserId, nowDate);
		mealHistoryService.updateMealHistory(mybreakfast2, mylunch2, mydinner2, wxUserId, calendar.getTime());
		returnMap.put("success", true);
		return returnMap;
	}
	
	@RequestMapping(value="/modifyMealDefault")
	@ResponseBody
	public Map<String,Object> modifyMealDefault(@CookieValue(value = "wxUserId", required = false) String wxUserId,String week0,String week1,String week2,String week3,String week4,String week5,String week6) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		mealDefaultService.modifyMealDefault(wxUserId, week0, week1, week2, week3, week4, week5, week6);
		returnMap.put("success", true);
		return returnMap;
	}
	

}
