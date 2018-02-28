package com.social.restexceptionhandler;

public class UserDataNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserDataNotFoundException(String exceptionMsg) {
		super(exceptionMsg);
	}
}
