package com.woxsen.codex.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.codex.entities.Event;

public interface EventService {
	public List<Event> findAll();
	
	public List<Event> findByDateRange(LocalDate startDate, LocalDate endDate);
	
	public Event findById(UUID id);
	
	public UUID addEvent(Event event);
	
	public boolean deleteById(UUID id);
	
	public Event updateById(UUID id, Event event);
	
	public String storeFile(MultipartFile file, UUID id);
	
	public Resource loadFileAsResource(String filename);
}
