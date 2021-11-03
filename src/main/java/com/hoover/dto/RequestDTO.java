package com.hoover.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RequestDTO .java - This is request POJO class for JSON
 * 
 * @author Ankita Singh
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDTO {
	
	@NotNull(message = "Room size cannot be null or empty")
	private Coordinates roomSize;
	
	@NotNull(message = "Coordinates cannot be null or empty")
	private Coordinates coords;
	
	@NotNull(message = "Patches cannot be null or empty")
	private List<Coordinates> patches;
	
	@NotNull(message = "Instructions cannot be null or empty")
	private String instructions;
}
