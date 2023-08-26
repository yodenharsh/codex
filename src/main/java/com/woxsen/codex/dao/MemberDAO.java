package com.woxsen.codex.dao;

import java.util.List;

import com.woxsen.codex.entities.Member;

public interface MemberDAO {
	
	public List<Member> findAll();
	
	public Member findById(int id);
	
	public int addMember(Member member);
	
	public boolean deleteById(int id);
	
	public Member updateMember(int id, Member member);
	
	//TODO add image based methods
}
