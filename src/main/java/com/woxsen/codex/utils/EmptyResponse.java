package com.woxsen.codex.utils;

public class EmptyResponse {
	
	private boolean success;
	
	private int statusCode;

	public EmptyResponse(boolean success, int statusCode) {
		this.success = success;
		this.statusCode = statusCode;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	
}
