package com.toolkit.sms.exceptions;

public class UrlNotSetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public UrlNotSetException() {
		super();
	}

	/**
	 * @param message
	 */
	public UrlNotSetException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return super.toString() + " : apiUrl is not set in SmsSender.";
	}

}
