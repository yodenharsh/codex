package com.woxsen.codex.entities;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="events")
@Entity
public class Event {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "id", nullable=false)
	private UUID id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "priority")
	private int priority;
	
	@Column(name = "url", nullable=true)
	private String url;
	
	@Column(name = "custom-field", nullable=true)
	private String customField;

	public Event(UUID id, String title, String description, int priority, String url, String customField) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.url = url;
		this.customField = customField;
	}

	public Event(String title, String description, int priority, String url, String customField, LocalDate date) {
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.url = url;
		this.customField = customField;
		this.date = date;
	}

	public Event() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCustomField() {
		return customField;
	}

	public void setCustomField(String customField) {
		this.customField = customField;
	}

	public Event(UUID id, String title, String description, LocalDate date, int priority, String url,
			String customField) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.date = date;
		this.priority = priority;
		this.url = url;
		this.customField = customField;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	
}
