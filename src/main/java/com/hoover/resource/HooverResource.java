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
import com.hoover.service.HooverService;
import com.hoover.service.HooverServiceImpl;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * HooverResource .java - This is rest controller which exposes methods
 * 
 * @author Ankita Singh
 * @version 1.0
 */
@RestController
@RequestMapping("hoover-service/v1")
public class HooverResource {

	private HooverService hooverService;

	private static final Logger LOGGER = Logger.getLogger(HooverResource.class.getName());

	@Autowired
	public HooverResource(final HooverService hooverService) {
		this.hooverService = hooverService;
	}

	/**
	 * This a http post endpoint that will perform navigate and clean operation
	 * 
	 * @return A ResponseDTO object.
	 */
	@PostMapping("clean")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully navigated"),
			@ApiResponse(responseCode = "400", description = "Invalid request") })
	public ResponseEntity<ResponseDTO> navigateAndClean(@Valid @RequestBody RequestDTO request) {
		LOGGER.info("Inside navigateAndClean method " + LOGGER.getName());
		return ResponseEntity.status(HttpStatus.OK).body(hooverService.navigateAndClean(request));
	}

}
