package com.zee.ordering.common;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseEntity {
	private Long id ;
	private Date createTime ;
	private Date updateTime ;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	} 
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public static <T extends BaseEntity> Map<Long, T> idEntityMap(Collection<T> list){
		Map<Long, T> map = new HashMap<Long, T>() ; 
		if(null == list || 0 == list.size()){
			return map ; 
		}
		for (T entity : list) {
			map.put(entity.getId(), entity) ; 
		}
		return map ;
	}
}
