package com.zee.ordering.dao;

import java.util.List;

import com.zee.ordering.entity.ClockHistory;

public interface ClockHistoryDao {
	
	public int save(List<ClockHistory> clockHistoryList);
	
	public List<ClockHistory> getClockHistoryByMonth(String clockMonth);
	
}
