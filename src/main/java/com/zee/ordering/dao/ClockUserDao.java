package com.zee.ordering.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ClockUserDao {
	
	/**
	 * 查询所有匹配用户
	 * @return
	 */
	public List<Map<String,Object>> getAllMatchUser();

	/**
	 * 查询是否已经匹配
	 * @param wxId 微信id
	 * @return
	 */
	public int existMatchUser(@Param("clockId")String clockId,@Param("clockName")String clockName);
	
	/**
	 * 保存
	 * @param wxId
	 * @param wxName
	 * @param clockId
	 * @param clockName
	 * @return
	 */
	public int save(@Param("wxId")String wxId,@Param("wxName")String wxName,@Param("clockId")String clockId,@Param("clockName")String clockName);
	
	/**
	 * 更新
	 * @param wxId
	 * @param wxName
	 * @param clockId
	 * @param clockName
	 * @return
	 */
	public int update(@Param("wxId")String wxId,@Param("wxName")String wxName,@Param("clockId")String clockId,@Param("clockName")String clockName);
}
