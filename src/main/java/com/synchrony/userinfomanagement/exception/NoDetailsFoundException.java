package com.synchrony.userinfomanagement.exception;

public class NoDetailsFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NoDetailsFoundException(String exceptionMessage) {
	     super(exceptionMessage);
		}
}
