package com.zee.ordering.service;

public interface AdminService {
	
	/**
	 * 验证用户
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean existUser(String username,String password);
	
}
