package com.woxsen.codex.rest;

import java.io.IOException;
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

import com.woxsen.codex.entities.Member;
import com.woxsen.codex.service.MemberService;
import com.woxsen.codex.utils.EmptyResponse;
import com.woxsen.codex.utils.FileResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MemberREST {

	private MemberService memberService;
    private static final Logger logger = LoggerFactory.getLogger(MemberREST.class);
    
	
	@Autowired
	public MemberREST(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/member")
	public List<Member> getAll(){
		return memberService.findAll();
	}
	
	@GetMapping("/member/{id}")
	public Member getOne(@PathVariable UUID id) {
		return memberService.findById(id);
	}
	
	@PostMapping("/member")
	public ResponseEntity<HashMap<String,UUID>> addOne(@RequestBody Member member){
		UUID id = memberService.addMember(member);
		
		HashMap<String, UUID> responseBody = new HashMap<>();
		responseBody.put("id", id);
		
		return ResponseEntity.ok(responseBody);
	}
	
	@DeleteMapping("/member/{id}")
	public EmptyResponse deleteOne(@PathVariable UUID id) {
		boolean success = memberService.deleteById(id);
		
		if(success)
			return new EmptyResponse(success, 200);
		else
			return new EmptyResponse(success, 500);
	}
	
	@PatchMapping("/member/{id}")
	public Member updateMember(@PathVariable UUID id, @RequestBody Member member) {
		Member updatedMember = memberService.updateMember(id, member);
		
		return updatedMember;
	}
	
	@PutMapping("/member/uploadFile/{id}")
	public FileResponse uploadFile(@RequestParam("image") MultipartFile file, @PathVariable UUID id) {
		String fileName = memberService.storeFile(file, id);
		
		String fileDownloaderUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	            .path("/api/member/downloadFile/")
	            .path(fileName)
	            .toUriString();
        return new FileResponse(fileName, fileDownloaderUri,
                file.getContentType(), file.getSize());
	}
	
	@GetMapping("/member/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
		
		Resource resource = memberService.loadFileAsResource(fileName);
		
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
