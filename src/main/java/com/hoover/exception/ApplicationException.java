package com.hoover.exception;


/**  
*ApplicationException .java - This is application specific exception class to handle all exceptions
* @author  Ankita Singh
* @version 1.0 
*/ 
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 5962208909588472810L;

	public ApplicationException(String errorMessage) {
		super(errorMessage);
	}

	public ApplicationException(String message, Throwable error) {
		super(message, error);
	}
}
