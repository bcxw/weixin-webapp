package com.zee.ordering.dao;

import org.apache.ibatis.annotations.Param;

public interface AdminDao {
	
	public int existUser(@Param("username") String username,@Param("password") String password);
	
}
