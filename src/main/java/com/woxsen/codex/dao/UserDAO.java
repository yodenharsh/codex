package com.woxsen.codex.dao;

import com.woxsen.codex.entities.User;

public interface UserDAO {

	public boolean addUser(User user);
	
	public boolean login(User user);
	
	public boolean deleteUser(User user);
}
