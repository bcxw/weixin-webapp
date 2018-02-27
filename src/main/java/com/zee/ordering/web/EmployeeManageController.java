package com.zee.ordering.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zee.ordering.common.AjaxResult;
import com.zee.ordering.service.EmployeeManageService;
import com.zee.ordering.service.MealManageService;

@Controller
@RequestMapping("/employeeManage")
public class EmployeeManageController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeManageController.class);

	@Resource
	private EmployeeManageService employeeManageService;
	
	@RequestMapping("index")
	public String index(HttpServletRequest request) {
		return "admin/employeeManage";
		
	}
	
	@RequestMapping("getTreeDepartments")
	@ResponseBody
	public List<Map<String,Object>> getTreeDepartments() {
		return employeeManageService.getTreeDepartments();
	}
	
	@RequestMapping(value = "/getEmployeeByDepart")
	@ResponseBody
	public Map<String, Object> getEmployeeByDepart(Integer page, Integer rows,Integer departmentId, String searchName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if(departmentId!=null){
				resultMap.put("success", true);
				resultMap.put("rows", employeeManageService.getEmployeeByDepart(departmentId, searchName, page == 1 ? (page - 1) : (page - 1) * rows, rows));
				resultMap.put("total", employeeManageService.getEmployeeByDepartTotal(departmentId, searchName));
			
			}else{
				resultMap.put("success", false);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	
	@RequestMapping("fireEmployee")
	@ResponseBody
	public AjaxResult fireEmployee(String wxUserId) {
		return employeeManageService.fireEmployee(wxUserId);
	}

}
