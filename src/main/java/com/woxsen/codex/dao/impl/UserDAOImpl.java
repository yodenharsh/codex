package com.woxsen.codex.dao.impl;

import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.woxsen.codex.dao.UserDAO;
import com.woxsen.codex.entities.User;
import com.woxsen.codex.exceptions.InvalidCredentialsException;

import jakarta.persistence.EntityManager;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	

	public UserDAOImpl(EntityManager entityManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.entityManager = entityManager;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}



	@Override
	public boolean addUser(User user) {
		Session session = entityManager.unwrap(Session.class);
		
		String encodedPw = this.bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPw);
		
		session.persist(user);
		
		return true;
	}

	@Override
	public boolean login(User user) {
		Session session = entityManager.unwrap(Session.class);
		
		SelectionQuery<User> q = session.createSelectionQuery("FROM User WHERE username= :u", User.class);
		
		q.setParameter("u", user.getUsername());
		try {
		User userFromDB = q.getSingleResult();
		
		if(this.bCryptPasswordEncoder.matches(user.getPassword(), userFromDB.getPassword()))
			return true;
		}
		catch(Exception e) {
			throw new InvalidCredentialsException("Invalid credentials");
		}
		
		throw new InvalidCredentialsException("Invalid credentials");
	}

	@Override
	public boolean deleteUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
}
