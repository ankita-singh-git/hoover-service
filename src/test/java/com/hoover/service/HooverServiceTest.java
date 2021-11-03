package com.hoover.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hoover.dto.RequestDTO;
import com.hoover.dto.ResponseDTO;
import com.hoover.exception.ValidationException;
import com.hoover.util.TestData;

@ExtendWith(MockitoExtension.class)
public class HooverServiceTest {

	private HooverService hooverService;

	@Mock
	private ValidationService validationService;

	@BeforeEach
	public void init() {
		hooverService = new HooverServiceImpl(validationService);
	}

	@Test
	public void shouldThrowExceptionWhenInvalidRequest() {
		RequestDTO requestWithInvalidData = TestData.buildInvalidPatchRequest();
		when(validationService.validateRequest(requestWithInvalidData))
				.thenThrow(new ValidationException("Provided start coords are not valid"));
		assertThrows(ValidationException.class, () -> hooverService.navigateAndClean(requestWithInvalidData),
				"Provided start coords are not valid");
	}

	@Test
	public void shouldThrowExceptionWhenInvalidDirections() {
		RequestDTO requestWithInvalidData = TestData.buildInvalidPatchRequest();
		requestWithInvalidData.setInstructions("ABCDEF");
		when(validationService.validateRequest(requestWithInvalidData)).thenReturn(Optional.of(true));
		assertThrows(ValidationException.class, () -> hooverService.navigateAndClean(requestWithInvalidData),
				"Invalid Direction Provided. Accepted values are N, S, E, W");
	}

	@Test
	public void shouldReturnResponseWhenValid() {
		RequestDTO requestWithInvalidData = TestData.buildValidRequest();
		ResponseDTO expectedResponse = TestData.buildValidResponse();
		
		when(validationService.validateRequest(requestWithInvalidData)).thenReturn(Optional.of(true));
		when(validationService.isCoordsValid(any(),any())).thenReturn(true);
		ResponseDTO responseDTO = hooverService.navigateAndClean(requestWithInvalidData);
		assertEquals(expectedResponse,responseDTO);
	}
}
