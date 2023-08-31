package com.woxsen.codex.rest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.woxsen.codex.entities.Event;
import com.woxsen.codex.service.EventService;
import com.woxsen.codex.utils.EmptyResponse;
import com.woxsen.codex.utils.FileResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class EventREST {
	
	private EventService eventService;
    private static final Logger logger = LoggerFactory.getLogger(MemberREST.class);


	@Autowired
	public EventREST(EventService eventService) {
		this.eventService = eventService;
	}
	
	@GetMapping("/event")
	public List<Event> getAll(){
		return eventService.findAll();
	}
	
	@GetMapping("/event/{id}")
	public Event getOne(@PathVariable UUID id) {
		return eventService.findById(id);
	}
	
	@PostMapping("/event/dateRange")
	public List<Event> getByDateRange(@RequestBody HashMap<String,String> dateList) {
		
		String startDateString = dateList.get("from");
		String endDateString = dateList.get("to");
		
		LocalDate start = null;
		LocalDate end = null;
		
		if(startDateString != null)
			start = LocalDate.parse(startDateString);
		if(endDateString != null)
			end = LocalDate.parse(endDateString);
		
		return eventService.findByDateRange(start, end);
	}
	
	@PostMapping("/event")
	public ResponseEntity<HashMap<String,String>> addOne(@RequestBody Event event){
		String id = String.valueOf(eventService.addEvent(event));
		
		HashMap<String,String> response = new HashMap<>();
		response.put("id", id);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/event/{id}")
	public EmptyResponse deleteEvent(@PathVariable UUID id) {
		boolean success = eventService.deleteById(id);
		
		if(success)
			return new EmptyResponse(success, 200);
		else
			return new EmptyResponse(success, 500);
	}
	
	@PatchMapping("/event/{id}")
	public Event updateEvent(@PathVariable UUID id, @RequestBody Event event) {
		return eventService.updateById(id, event);
	}
	
	@PutMapping("/event/uploadFile/{id}")
	public FileResponse uploadFile(@RequestParam("image") MultipartFile file, @PathVariable UUID id) {
		String fileName = eventService.storeFile(file, id);
		
		String fileDownloaderUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	            .path("/api/event/downloadFile/")
	            .path(fileName)
	            .toUriString();
        return new FileResponse(fileName, fileDownloaderUri,
                file.getContentType(), file.getSize());
	}
	
	@GetMapping("/event/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
		
		Resource resource = eventService.loadFileAsResource(fileName);
		
		String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
	}
}
