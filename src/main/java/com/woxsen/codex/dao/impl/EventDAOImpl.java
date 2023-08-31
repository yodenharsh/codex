package com.woxsen.codex.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.woxsen.codex.dao.EventDAO;
import com.woxsen.codex.entities.Event;
import com.woxsen.codex.exceptions.RecordNotFoundException;

import jakarta.persistence.EntityManager;

@Repository
public class EventDAOImpl implements EventDAO {

	private EntityManager entityManager;
	
	
	@Autowired
	public EventDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Event> findAll() {
		Session session = entityManager.unwrap(Session.class);
		
		SelectionQuery<Event> q = session.createSelectionQuery("FROM Event", Event.class);
		
		List<Event> events = q.getResultList();
		
		return events;
	}

	@Override
	public List<Event> findByDateRange(LocalDate startDate, LocalDate endDate) {
		Session session = entityManager.unwrap(Session.class);
		
		String baseQuery = "from Event e where 1=1 AND";
		
		boolean startFlag = startDate!=null;
		boolean endFlag = endDate!=null;
		
		if(startFlag) 
			baseQuery += " e.date >= :start AND";
		if(endFlag)
			baseQuery += " e.date <= :end AND";
		baseQuery += " 0=0 ORDER BY e.date DESC";
				
		SelectionQuery<Event> q = session.createSelectionQuery(baseQuery, Event.class);
		
		if(startFlag)
			q.setParameter("start", startDate);
		if(endFlag)
			q.setParameter("end", endDate);
		
		List<Event> events = q.getResultList();
		
		return events;
	}

	@Override
	public Event findById(UUID id) {
		Session session = entityManager.unwrap(Session.class);
		
		Event event = session.get(Event.class, id);
		return event;
	}

	@Override
	public UUID addEvent(Event event) {
		Session session = entityManager.unwrap(Session.class);
		
		session.persist(event);
		
		return event.getId();
	}

	@Override
	public boolean deleteById(UUID id) {
		Session session = entityManager.unwrap(Session.class);
		
		Event event = session.get(Event.class, id);
		if(event == null)
			throw new RecordNotFoundException("Event with id="+id+" was not found");
		
		session.remove(event);
		return true;
	}

	@Override
	public Event updateById(UUID id, Event event) {
		Session session = entityManager.unwrap(Session.class);
		
		Event eventFromDB = session.get(Event.class, id);
		if(event == null)
			throw new RecordNotFoundException("Event with id="+id+" was not found");
		
		eventFromDB.setCustomField(event.getCustomField());
		eventFromDB.setDate(event.getDate());
		eventFromDB.setDescription(event.getDescription());
		eventFromDB.setPriority(event.getPriority());
		eventFromDB.setTitle(event.getTitle());
		eventFromDB.setUrl(event.getUrl());
		
		return eventFromDB;
	}

}
