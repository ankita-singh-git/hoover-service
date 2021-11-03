package com.hoover.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoover.dto.ErrorResponse;

/**  
*GeneralExceptionHandler .java - This handles all type of exception and return a ErrorResponse
* @author  Ankita Singh
* @version 1.0 
*/
@ControllerAdvice
public class GeneralExceptionHandler {
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public HttpEntity<ErrorResponse> handleAny(HttpServletRequest request, Exception ex) {
		final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ResponseBody
	@ExceptionHandler(ValidationException.class)
	public HttpEntity<ErrorResponse> handleValidationException(HttpServletRequest request, Exception ex) {
		final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
