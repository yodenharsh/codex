package com.woxsen.codex.service;

import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.codex.entities.Member;

public interface MemberService {
	public List<Member> findAll();
	
	public Member findById(UUID id);
	
	public UUID addMember(Member member);
	
	public boolean deleteById(UUID id);
	
	public Member updateMember(UUID id, Member member);
	
	public String storeFile(MultipartFile file, UUID id);
	
	public Resource loadFileAsResource(String filename);
}
