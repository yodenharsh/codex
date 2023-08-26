package com.woxsen.codex.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.woxsen.codex.exceptions.InvalidCredentialsException;
import com.woxsen.codex.utils.ErrorResponse;

@RestControllerAdvice
public class RESTGlobalExceptionHandler {
	
	@ExceptionHandler(value = InvalidCredentialsException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ErrorResponse entityNotFoundExceptionHandler(InvalidCredentialsException e, WebRequest request) {
		ErrorResponse erMsg = new ErrorResponse(HttpStatus.UNAUTHORIZED,e.getMessage(), "false");
		return erMsg;
	}
}
