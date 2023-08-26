package com.woxsen.codex.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import com.woxsen.codex.utils.MemberRoles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class Member {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", type = UuidGenerator.class)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name",nullable = false)
	private String name;
	
	@Column(name = "linkedin-url")
	private String linkedinURL;
	
	@Column(name = "github-url")
	private String githubURL;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "member-role")
	private MemberRoles memberRole;

	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLinkedinURL() {
		return linkedinURL;
	}

	public void setLinkedinURL(String linkedinURL) {
		this.linkedinURL = linkedinURL;
	}

	public String getGithubURL() {
		return githubURL;
	}

	public void setGithubURL(String githubURL) {
		this.githubURL = githubURL;
	}

	public MemberRoles getMemberRole() {
		return memberRole;
	}

	public void setMemberRole(MemberRoles memberRole) {
		this.memberRole = memberRole;
	}

	public Member(int id, String name, String linkedinURL, String githubURL, MemberRoles memberRole) {
		this.id = id;
		this.name = name;
		this.linkedinURL = linkedinURL;
		this.githubURL = githubURL;
		this.memberRole = memberRole;
	}

	public Member() {
	}

	public Member(String name, String linkedinURL, String githubURL, MemberRoles memberRole) {
		this.name = name;
		this.linkedinURL = linkedinURL;
		this.githubURL = githubURL;
		this.memberRole = memberRole;
	}
    
    
}
