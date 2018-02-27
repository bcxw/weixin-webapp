package com.zee.ordering.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zee.ordering.dao.UserDao;
import com.zee.ordering.entity.User;
import com.zee.ordering.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;

	@Override
	public User findUser(String wxUserId) {
		return userDao.findUser(wxUserId);
	}

	@Override
	public int update(User user,List<Long> departmets) {
		int num = userDao.update(user);
		long id=user.getId();
		userDao.deleteDepartments(id);
		userDao.saveDepartments(id, departmets);
		return num;
	}

	@Override
	public int save(User user,List<Long> departmentIds) {
		int num=userDao.save(user);
		long id=user.getId();
		userDao.saveDepartments(id, departmentIds);
		return num;
	}

	@Override
	public Map<String, Object> getUserDetail(String wxUserId) {
		List<Map<String,Object>> list = userDao.getUserDetail(wxUserId);
		return list!=null&&!list.isEmpty()?list.get(0):new HashMap<String,Object>();
	}
	
}
