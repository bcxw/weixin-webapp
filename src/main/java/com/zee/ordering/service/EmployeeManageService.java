package com.zee.ordering.service;

import java.util.List;
import java.util.Map;

import com.zee.ordering.common.AjaxResult;
import com.zee.ordering.entity.User;

public interface EmployeeManageService {
	
	
	public List<Map<String,Object>> getTreeDepartments();
	
	public List<User> getEmployeeByDepart(int departmentId,String searchName,int start,int limit);
	
	public int getEmployeeByDepartTotal(int departmentId,String searchName);
	
	/**
	 * 标记人员离职 1将微信和本地的人员扔到离职部门中2删除未来报餐记录
	 * @param wxUserId
	 * @return
	 */
	public AjaxResult fireEmployee(String wxUserId);
}
