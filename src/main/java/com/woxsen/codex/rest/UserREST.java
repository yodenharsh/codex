package com.woxsen.codex.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woxsen.codex.entities.User;
import com.woxsen.codex.service.UserService;
import com.woxsen.codex.utils.EmptyResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserREST {

	@Autowired
	private UserService userService;

	public UserREST(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/user")
	public EmptyResponse addUser(@RequestBody User user) {
				
		boolean success = userService.addUser(user);
		
		if(success) 
			return new EmptyResponse(success, 200);
		else
			return new EmptyResponse(success, 401);
	}
	
	@PostMapping("/user/login")
	public EmptyResponse loginUser(@RequestBody User user) {
		boolean success = userService.login(user);
		
		if(success) 
			return new EmptyResponse(success, 200);
		else
			return new EmptyResponse(success, 401);
	}
	
	@DeleteMapping("/user/login")
	public EmptyResponse deleteUser(@RequestBody User user) {
		boolean success = userService.deleteUser(user);
		
		if(success)
			return new EmptyResponse(success, 200);
		else
			return new EmptyResponse(success,401);
	}
}
