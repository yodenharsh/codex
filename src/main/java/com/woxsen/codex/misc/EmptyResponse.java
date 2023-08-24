package com.woxsen.codex.misc;

public class EmptyResponse {
	
	private boolean success;
	
	private String statusCode;

	public EmptyResponse(boolean success, String statusCode) {
		this.success = success;
		this.statusCode = statusCode;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	
}
