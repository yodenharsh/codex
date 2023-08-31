package com.woxsen.codex.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.woxsen.codex.entities.Event;

public interface EventDAO {
	
	public List<Event> findAll();
	
	public List<Event> findByDateRange(LocalDate startDate, LocalDate endDate);
	
	public Event findById(UUID id);
	
	public UUID addEvent(Event event);
	
	public boolean deleteById(UUID id);
	
	public Event updateById(UUID id, Event event);
	
}
