package com.hoover.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**  
*ResponseDTO .java - This is response POJO class for JSON
* @author  Ankita Singh
* @version 1.0 
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
	
	private Coordinates coords;
	private int patches;
}
