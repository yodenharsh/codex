package com.woxsen.codex.service;

import com.woxsen.codex.entities.User;

public interface UserService {
	
	public boolean addUser(User user);
	
	public boolean login(User user);
	
	public boolean deleteUser(User user);
}
