package com.zee.ordering.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zee.ordering.common.AjaxResult;
import com.zee.ordering.common.WeixinUtil;
import com.zee.ordering.context.ConfigContext;
import com.zee.ordering.dao.DepartmentDao;
import com.zee.ordering.dao.MealHistoryDao;
import com.zee.ordering.dao.UserDao;
import com.zee.ordering.entity.Department;
import com.zee.ordering.entity.User;
import com.zee.ordering.service.EmployeeManageService;

@Service
public class EmployeeManageServiceImpl implements EmployeeManageService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeManageServiceImpl.class);
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private DepartmentDao departmentDao;
	
	@Resource
	private MealHistoryDao mealHistoryDao;
	
	@Resource
	private ConfigContext configContext;
	
	@Override
	public List<Map<String,Object>> getTreeDepartments() {
		List<Department> departmentList=departmentDao.getAllDepartments();
		return createDepartmentTreeData(0,departmentList);
	}
	
	private List<Map<String,Object>> createDepartmentTreeData(int parentId,List<Department> departmentList){
		List<Map<String,Object>> treeDataList=new ArrayList<Map<String,Object>>();
		
		for(int i=0;i<departmentList.size();i++){
			Department oneDep=departmentList.get(i);
			if(oneDep.getParentId()==parentId){
				Map<String,Object> oneNode=new HashMap<String,Object>();
				oneNode.put("id", oneDep.getId());
				oneNode.put("text", oneDep.getName());
				oneNode.put("children", createDepartmentTreeData(Integer.parseInt(oneDep.getId()+""),departmentList));
				
				treeDataList.add(oneNode);
			}
		}
		
		return treeDataList;
	}

	@Override
	public List<User> getEmployeeByDepart(int departmentId, String searchName, int start, int limit) {
		return userDao.getEmployeeByDepart(departmentId, searchName, start, limit);
	}

	@Override
	public int getEmployeeByDepartTotal(int departmentId, String searchName) {
		return userDao.getEmployeeByDepartTotal(departmentId, searchName);
	}

	@Override
	public AjaxResult fireEmployee(String wxUserId) {
		/*****1.修改微信端移到离职部门****/
		Map<String,Object> modifyUserMap=new HashMap<String,Object>();
		modifyUserMap.put("userid", wxUserId);
		
		List<Long> leaveDepartmentList=new ArrayList<Long>();
		leaveDepartmentList.add(Long.parseLong(configContext.getLeaveDepartmentId()));
		modifyUserMap.put("department", leaveDepartmentList);
		
		boolean isWeixinModify=WeixinUtil.modifyUser(modifyUserMap);
		if(isWeixinModify){
			
			/*****2.本地部门改成离职部门*******/
			User user = userDao.findUser(wxUserId);
			long id = user.getId();
			userDao.deleteDepartments(id);
			userDao.saveDepartments(id, leaveDepartmentList);
			
			/*****3.删除今天明天的报餐记录*******/
			Calendar calendar = Calendar.getInstance(); 
			calendar.setTime(new Date());
			
			mealHistoryDao.deleteByWeixinIdDate(wxUserId, calendar.getTime());
			calendar.add(Calendar.DATE, 1);
			mealHistoryDao.deleteByWeixinIdDate(wxUserId, calendar.getTime());
			return AjaxResult.success("操作成功！");
		}else{
			return AjaxResult.error("修改微信端人员部门失败！");
		}
	}
	

}
