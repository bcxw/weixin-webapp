package com.zee.ordering.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zee.ordering.common.MD5Utils;
import com.zee.ordering.dao.AdminDao;
import com.zee.ordering.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Resource
	private AdminDao adminDao;

	@Override
	public boolean existUser(String username, String password) {
		password=MD5Utils.MD5(password);
		return adminDao.existUser(username, password) >= 1 ? true : false;
	}

}
