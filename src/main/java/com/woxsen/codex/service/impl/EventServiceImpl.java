package com.woxsen.codex.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.codex.configuration.StorageProperties;
import com.woxsen.codex.dao.EventDAO;
import com.woxsen.codex.entities.Event;
import com.woxsen.codex.exceptions.FileNotFoundException;
import com.woxsen.codex.exceptions.FileStorageException;
import com.woxsen.codex.service.EventService;

import jakarta.transaction.Transactional;

@Service
public class EventServiceImpl implements EventService {

	private EventDAO eventDAO;
	private final Path fileStorageLocation;

	
	@Autowired
	public EventServiceImpl(EventDAO eventDAO, StorageProperties storageProperties) {
		this.eventDAO = eventDAO;
		this.fileStorageLocation = Paths.get(storageProperties.getEventImage()).toAbsolutePath().normalize();
		
		try {
			System.out.println(fileStorageLocation);
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	@Override
	@Transactional
	public List<Event> findAll() {
		return eventDAO.findAll();
	}

	@Override
	@Transactional
	public List<Event> findByDateRange(LocalDate startDate, LocalDate endDate) {
		return eventDAO.findByDateRange(startDate, endDate);
	}

	@Override
	@Transactional
	public Event findById(UUID id) {
		return eventDAO.findById(id);
	}

	@Override
	@Transactional
	public UUID addEvent(Event event) {
		return eventDAO.addEvent(event);
	}

	@Override
	@Transactional
	public boolean deleteById(UUID id) {
		return eventDAO.deleteById(id);
	}

	@Override
	@Transactional
	public Event updateById(UUID id, Event event) {
		return eventDAO.updateById(id, event);
	}

	@Override
	public String storeFile(MultipartFile file, UUID id) {
		String extension = file.getContentType().substring(file.getContentType().indexOf('/') + 1,
				file.getContentType().length());

		String fileName = String.valueOf(id) + "." + extension;

		try {
			if (fileName.contains(".."))
				throw new FileStorageException("Filename contains invalid sequence");

			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			System.out.println();
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException("Couldn't store " + fileName + ". Please try again");
		}
	}

	@Override
	public Resource loadFileAsResource(String filename) {
		String fileName = String.valueOf(filename);
		try {

			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("File not found " + fileName, ex);
		}
	}

}
