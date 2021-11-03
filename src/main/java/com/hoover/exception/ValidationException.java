package com.hoover.exception;


/**  
*ValidationException .java - This a runtime exception which is thrown in case of any validation errors
* @author  Ankita Singh
* @version 1.0 
*/ 
public class ValidationException extends  RuntimeException{
	private static final long serialVersionUID = 5962208909588472810L;

	public ValidationException(String errorMessage) {
		super(errorMessage);
	}

	public ValidationException(String message, Throwable error) {
		super(message, error);
	}
}
