package com.hoover.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ErrorResponse .java - This is the POJO for Json response that's returned in case of any error
 * 
 * @author Ankita Singh
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
	private Integer errorCode;
	private String message;
}
