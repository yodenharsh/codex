package com.woxsen.codex.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.woxsen.codex.dao.MemberDAO;
import com.woxsen.codex.entities.Member;
import com.woxsen.codex.exceptions.RecordNotFoundException;

import jakarta.persistence.EntityManager;

@Repository
public class MemberDAOImpl implements MemberDAO {

	private EntityManager entityManager;

	@Autowired
	public MemberDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Member> findAll() {

		Session session = entityManager.unwrap(Session.class);

		SelectionQuery<Member> q = session.createSelectionQuery("FROM Member", Member.class);

		List<Member> members = q.getResultList();

		return members;
	}

	@Override
	public Member findById(UUID id) {

		Session session = entityManager.unwrap(Session.class);

		Member member = session.get(Member.class, id);

		if (member == null)
			throw new RecordNotFoundException("Record with id = " + id + " was not found");

		return member;
	}

	@Override
	public UUID addMember(Member member) {

		Session session = entityManager.unwrap(Session.class);

		session.persist(member);

		return member.getid();
	}

	@Override
	public boolean deleteById(UUID id) {

		Session session = entityManager.unwrap(Session.class);
		try {
			Member member = session.get(Member.class, id);

			if (member == null)
				throw new RecordNotFoundException("Record with id = " + id + " not found");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Member updateMember(UUID id, Member member) {
		
		Session session = entityManager.unwrap(Session.class);
		
		Member memberInDB = session.get(Member.class, id);
		
		if(memberInDB == null)
			throw new RecordNotFoundException("Member with id = "+id+ " was not found!");
		
		
		memberInDB.setGithubURL(member.getGithubURL());
		memberInDB.setLinkedinURL(member.getLinkedinURL());
		memberInDB.setName(member.getName());
		memberInDB.setMemberRole(member.getMemberRole());
		
		return member;
	}

}
