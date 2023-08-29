package com.woxsen.codex.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.woxsen.codex.exceptions.FileNotFoundException;
import com.woxsen.codex.exceptions.FileStorageException;
import com.woxsen.codex.exceptions.InvalidCredentialsException;
import com.woxsen.codex.exceptions.RecordNotFoundException;
import com.woxsen.codex.utils.ErrorResponse;

@RestControllerAdvice
public class RESTGlobalExceptionHandler {
	
	@ExceptionHandler(value = InvalidCredentialsException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ErrorResponse entityNotFoundExceptionHandler(InvalidCredentialsException e, WebRequest request) {
		ErrorResponse erMsg = new ErrorResponse(HttpStatus.UNAUTHORIZED,e.getMessage(), "false");
		return erMsg;
	}
	
	@ExceptionHandler(value = FileNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorResponse fileNotFoundExceptionHandler(FileNotFoundException e, WebRequest request) {
		ErrorResponse erMsg = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage() , "false");
		return erMsg;
	}
	
	@ExceptionHandler(value = RecordNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorResponse recordNotFoundExceptionHandler(RecordNotFoundException e, WebRequest request) {
		ErrorResponse erMsg = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), "false");
		return erMsg;
	}
	
	@ExceptionHandler(value = FileStorageException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse fileStorageExceptionHandler(FileStorageException e, WebRequest request) {
		ErrorResponse erMsg = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "false");
		return erMsg;
	}
}
