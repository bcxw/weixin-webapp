package com.zee.ordering.entity;

import com.zee.ordering.common.BaseEntity;

/**
 * 部门信息
 * 
 * @author houyong
 */
public class Department extends BaseEntity {
	private String name;
	private int parentId;
	private int order;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
