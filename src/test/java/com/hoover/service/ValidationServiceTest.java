package com.hoover.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.hoover.dto.RequestDTO;
import com.hoover.exception.ValidationException;
import com.hoover.util.TestData;

public class ValidationServiceTest {

	private static ValidationService validationService;

	@BeforeAll
	public static void init() {
		validationService = new ValidationServiceImpl();
	}

	@ParameterizedTest
	@ValueSource(ints = { -1, 0, 5 })
	public void shouldReturnExceptionWhenRoomsizeIsInvalid(int invalidCoordinate) {
		RequestDTO requestWithInvalidRoomsize = TestData.buildValidRequest();
		requestWithInvalidRoomsize.setRoomSize(TestData.buildCoordinates(2, invalidCoordinate));
		assertThrows(ValidationException.class, () -> validationService.validateRequest(requestWithInvalidRoomsize),
				"Room size provided is invalid");
	}

	@Test
	public void shouldReturnExceptionWhenProvidedStartCoordsIsInvalid() {
		RequestDTO requestWithInvalidCoord = TestData.buildValidRequest();
		requestWithInvalidCoord.setCoords(TestData.buildCoordinates(1, -1));
		assertThrows(ValidationException.class, () -> validationService.validateRequest(requestWithInvalidCoord),
				"Provided start coords are not valid");
	}

	@Test
	public void shouldReturnExceptionWhenCoordsProvidedAreGreaterThanRoomSize() {
		RequestDTO requestWithCoordGreaterThanRoomSize = TestData.buildValidRequest();
		requestWithCoordGreaterThanRoomSize.setCoords(TestData.buildCoordinates(6, 6));
		assertThrows(ValidationException.class,
				() -> validationService.validateRequest(requestWithCoordGreaterThanRoomSize),
				"Provided start coords are not valid");
	}

	@Test
	public void shouldReturnExceptionWhenCoordsAreNull() {
		RequestDTO requestWithCoordsNull = TestData.buildValidRequest();
		requestWithCoordsNull.setCoords(null);
		assertThrows(ValidationException.class, () -> validationService.validateRequest(requestWithCoordsNull),
				"Provided start coords are not valid");
	}

	@Test
	public void shouldThrowExceptionWhenOnePatchIsInvalid() {
		RequestDTO invalidPatchRequest = TestData.buildWithOneInvalidPatchRequest();
		assertThrows(ValidationException.class, () -> validationService.validateRequest(invalidPatchRequest).get(),
				"Invalid patches present");
	}

	@Test
	public void shouldThrowExceptionWhenPatchesAreInvalid() {
		RequestDTO invalidPatchRequest = TestData.buildInvalidPatchRequest();
		assertThrows(ValidationException.class, () -> validationService.validateRequest(invalidPatchRequest).get(),
				"Invalid patches present");
	}

	@Test
	public void shouldReturnTrueWhenValid() {
		RequestDTO validPatchRequest = TestData.buildValidRequest();
		assertTrue(validationService.validateRequest(validPatchRequest).get());
	}
}
