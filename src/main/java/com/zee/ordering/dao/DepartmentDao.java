package com.zee.ordering.dao;

import java.util.List;

import com.zee.ordering.entity.Department;

public interface DepartmentDao {

	public int existDepartment(Department department);
	
	public int insert(Department department);
	
	public int update(Department department);
	
	/**
	 * 查询所有部门
	 * @return
	 */
	public List<Department> getAllDepartments();
}
