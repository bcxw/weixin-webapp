package com.zee.ordering.service;

import java.util.List;
import java.util.Map;

import com.zee.ordering.entity.User;

public interface UserService {
	
	/**
	 * 根据微信端的用户id查找用户，注意不是微信号
	 * @param wxUserId 微信端的用户id
	 * @return 用户
	 */
	public User findUser(String wxUserId) ; 
	
	/**
	 * 更新用户
	 * @param user 用户
	 */
	public int update(User user,List<Long> departmets);
	
	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	public int save(User user,List<Long> departmentIds);
	
	
	/**
	 * 查询用户明细
	 * @param wxUserId 微信id
	 * @return 详细信息
	 */
	public Map<String,Object> getUserDetail(String wxUserId);
	
}
