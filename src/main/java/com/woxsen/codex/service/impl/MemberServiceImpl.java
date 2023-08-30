package com.woxsen.codex.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.woxsen.codex.configuration.StorageProperties;
import com.woxsen.codex.dao.MemberDAO;
import com.woxsen.codex.entities.Member;
import com.woxsen.codex.exceptions.FileNotFoundException;
import com.woxsen.codex.exceptions.FileStorageException;
import com.woxsen.codex.service.MemberService;

import jakarta.transaction.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

	private final Path fileStorageLocation;
	
	private MemberDAO memberDAO;

	@Autowired
	public MemberServiceImpl(StorageProperties storageProperties, MemberDAO memberDAO) {
		this.fileStorageLocation = Paths.get(storageProperties.getMemberPhotoImage()).toAbsolutePath().normalize();
		this.memberDAO = memberDAO;

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
	public List<Member> findAll() {
		return memberDAO.findAll();
	}

	@Override
	@Transactional
	public Member findById(UUID id) {
		return memberDAO.findById(id);
	}

	@Override
	@Transactional
	public UUID addMember(Member member) {
		return memberDAO.addMember(member);
	}

	@Override
	@Transactional
	public boolean deleteById(UUID id) {
		return memberDAO.deleteById(id);
	}

	@Override
	@Transactional
	public Member updateMember(UUID id, Member member) {
		return memberDAO.updateMember(id, member);
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