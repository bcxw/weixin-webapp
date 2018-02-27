package com.zee.ordering.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zee.ordering.entity.User;

public interface UserDao {
	
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<User> getAllUser();

	/**
	 * 根据微信端的用户id查找用户，注意不是微信号
	 * 
	 * @param wxUserId
	 *            微信端的用户id
	 * @return 用户
	 */
	public User findUser(@Param("wxUserId") String wxUserId);

	/**
	 * 更新用户
	 * 
	 * @param user
	 *            用户
	 * @return int
	 */
	public int update(User user);

	/**
	 * 保存用户
	 * 
	 * @param user
	 *            用户
	 * @return int
	 */
	public int save(User user);

	/**
	 * 获取所有有效客户
	 * 
	 * @return
	 */
	public List<Map<String,Object>> findAllValidUser();
	
	/**
	 * 查询用户明细
	 * @param wxUserId 微信id
	 * @return 详细信息
	 */
	public List<Map<String,Object>> getUserDetail(String wxUserId);

	/**
	 * 保存用户部门
	 * 
	 * @param userId
	 *            用户id
	 * @param departmentIds
	 *            部门id
	 * @return num
	 */
	public int saveDepartments(@Param("userId")long userId, @Param("departmentIds")List<Long> departmentIds);
	
	/**
	 * 删除人员部门
	 * @param userId 人员id
	 * @return num
	 */
	public int deleteDepartments(@Param("userId")long userId);
	
	/**
	 * 按部门查询员工
	 * @param departmentId
	 * @param searchName
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<User> getEmployeeByDepart(@Param("departmentId")int departmentId, @Param("searchName")String searchName, @Param("start")int start, @Param("limit")int limit);
	
	/**
	 * 按部门查询员工总数
	 * @param departmentId
	 * @param searchName
	 * @return
	 */
	public int getEmployeeByDepartTotal(@Param("departmentId")int departmentId,@Param("searchName")String searchName);
}
