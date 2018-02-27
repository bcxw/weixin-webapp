package com.zee.ordering.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zee.ordering.common.AjaxResult;
import com.zee.ordering.service.MealManageService;

@Controller
@RequestMapping("/mealManage")
public class MealManageController {

	private static final Logger logger = LoggerFactory.getLogger(MealManageController.class);

	@Resource
	private MealManageService mealManageService;
	
	@RequestMapping("accountMeal")
	public String index(HttpServletRequest request) {
		return "admin/accountMeal";
		
	}
	
	@RequestMapping("importMealRecord")
	@ResponseBody
	public AjaxResult importMealRecord(@RequestParam MultipartFile file,HttpServletRequest request) {
		try {
			return mealManageService.importClockHistory(file.getInputStream());
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			return AjaxResult.error(e.getMessage());
		}
		
		
	}
	
	@RequestMapping("submitMatchEmpData")
	@ResponseBody
	public AjaxResult submitMatchEmpData(String clockMonth,String matchEmpData) {
		
		try {
			List<Map<String, Object>> empList =new ArrayList<Map<String, Object>>();
			ObjectMapper mapper = new ObjectMapper();
			empList= mapper.readValue(matchEmpData,empList.getClass());
			return mealManageService.mealClockAccount(clockMonth, empList);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(),e);
			return AjaxResult.error(e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(),e);
			return AjaxResult.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			return AjaxResult.error(e.getMessage());
		}
		
		
		
	}
	
	
	@RequestMapping(value = "/getMealClockAccount")
	@ResponseBody
	public Map<String, Object> getMealClockAccount(String month,String name,Integer page, Integer rows) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			resultMap.put("success", true);
			resultMap.put("rows", mealManageService.getAllMealAccount( month, name,page == 1 ? (page - 1) : (page - 1) * rows, rows));
			resultMap.put("total", mealManageService.getAllMealAccountTotal(month, name));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}
	
	
	@RequestMapping(value = "/exportMealAccount")
	public void exportMealAccount(HttpServletRequest request, HttpServletResponse response,String month) {
		HSSFWorkbook wb=mealManageService.exportMealAccount(month);
		OutputStream myout=null;
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("texthtml;charset=utf-8");
			myout=response.getOutputStream();
			
			response.setContentType("applicationoctet-stream;charset=UTF-8");
			String name = month+"报餐乐捐汇总表.xls";
			String userAgent = request.getHeader("User-Agent").toLowerCase();  
			byte[] bytes = userAgent.contains("safari") ?name.getBytes("UTF-8"):name.getBytes("gbk");
			name = new String(bytes, "ISO-8859-1");
			response.setHeader("Content-disposition", String.format("attachment;filename=\"%s\"", name));
			wb.write(myout);
			
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		} finally{
			try {
				if(wb!=null)wb.close();
				if(myout!=null)myout.close();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
           
	}
	
	

}
