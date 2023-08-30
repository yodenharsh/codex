package com.woxsen.codex.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woxsen.codex.dao.UserDAO;
import com.woxsen.codex.entities.User;
import com.woxsen.codex.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userDAO;
	
	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	@Transactional
	public boolean addUser(User user) {
		return userDAO.addUser(user);
	}

	@Override
	@Transactional
	public boolean login(User user) {
		return userDAO.login(user);
	}

	@Override
	@Transactional
	public boolean deleteUser(User user) {
		return userDAO.deleteUser(user);
	}

}
