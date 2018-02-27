package com.zee.ordering.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zee.ordering.common.MD5Utils;
import com.zee.ordering.service.AdminService;
import com.zee.ordering.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Resource
	private AdminService adminService;
	
	@RequestMapping("")
	public String index(HttpServletRequest request) {
		Object admin=request.getSession().getAttribute("admin");
		if(admin!=null&&StringUtils.isNotEmpty(admin+"")){
			return "admin/main";
		}else{
			return "admin/login";
		}
		
	}
	
	@RequestMapping("main")
	public String main(HttpServletRequest request) {
		Object admin=request.getSession().getAttribute("admin");
		if(admin!=null&&StringUtils.isNotEmpty(admin+"")){
			return "admin/main";
		}else{
			return "admin/login";
		}
		
	}
	
	@RequestMapping("login")
	public String login(HttpServletRequest request,Model model) {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		if(StringUtils.isNotEmpty(username)&&StringUtils.isNotEmpty(password)){
			if(adminService.existUser(username, password)){
				request.getSession().setAttribute("admin", username);
				return "redirect:/admin/main";
			}else{
				model.addAttribute("errMsg", "用户名或密码错误");
				return "admin/login";
			}
		}else{
			model.addAttribute("errMsg", "用户名和密码不能为空");
			return "admin/login";
		}
	}
	

}
