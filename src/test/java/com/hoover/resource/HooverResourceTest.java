package com.hoover.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.hoover.dto.RequestDTO;
import com.hoover.dto.ResponseDTO;
import com.hoover.exception.ValidationException;
import com.hoover.service.HooverService;
import com.hoover.util.TestData;

@ExtendWith(MockitoExtension.class)
public class HooverResourceTest {

	private HooverResource hooverResource;

	@Mock
	private HooverService hooverService;

	@BeforeEach
	public void init() {
		hooverResource = new HooverResource(hooverService);
	}

	@Test
	public void shouldReturn200WithData() {
		ResponseDTO expectedResponse = TestData.buildValidResponse();
		RequestDTO requestDTO = TestData.buildValidRequest();

		when(hooverService.navigateAndClean(requestDTO)).thenReturn(expectedResponse);

		ResponseEntity<ResponseDTO> response = hooverResource.navigateAndClean(requestDTO);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(expectedResponse, response.getBody());
	}

	@Test
	public void shouldThrowException() {
		RequestDTO requestDTO = TestData.buildInvalidPatchRequest();

		when(hooverService.navigateAndClean(requestDTO))
				.thenThrow(new ValidationException("Invalid patches present"));

		assertThrows(ValidationException.class, () -> hooverResource.navigateAndClean(requestDTO),
				"Invalid patches present");
	}

}
