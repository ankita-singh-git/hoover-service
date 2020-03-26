package com.hoover.resource;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoover.dto.RequestDTO;
import com.hoover.dto.ResponseDTO;
import com.hoover.exception.ApplicationException;
import com.hoover.service.HooverService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * HooverResource .java - This is rest controller which exposes methods
 * 
 * @author Ankita Singh
 * @version 1.0
 */
@RestController
@RequestMapping("hoover")
public class HooverResource {

	@Autowired
	private HooverService hooverService;

	private static final Logger LOGGER = Logger.getLogger(HooverResource.class.getName());

	/**
	 * This a http post method that calls service
	 * 
	 * @return A ResponseDTO object.
	 */
	@PostMapping
	@ApiOperation(value = "Navigates and cleans dirt")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully navigated"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Object> navigateAndClean(@Valid @RequestBody RequestDTO request) {
		LOGGER.info("Inside navigateAndClean method " + LOGGER.getName());
		ResponseDTO response;
		try {
			response = hooverService.navigateAndClean(request);

		} catch (ApplicationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
