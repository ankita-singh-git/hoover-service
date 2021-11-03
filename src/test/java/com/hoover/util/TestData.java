package com.hoover.util;

import java.util.Arrays;
import java.util.List;

import com.hoover.dto.Coordinates;
import com.hoover.dto.ErrorResponse;
import com.hoover.dto.RequestDTO;
import com.hoover.dto.ResponseDTO;

public class TestData {

	public static RequestDTO buildValidRequest() {
		return RequestDTO.builder().coords(buildCoordinates(1, 2)).roomSize(buildCoordinates(5, 5))
				.patches(buildPatches()).instructions("NNESEESWNWW").build();
	}

	public static ResponseDTO buildValidResponse() {
		return ResponseDTO.builder().coords(buildCoordinates(1, 3)).patches(1).build();
	}

	public static ErrorResponse buildErrorResponse() {
		return ErrorResponse.builder().errorCode(400).message("Coordinates provided are invalid").build();
	}

	public static RequestDTO buildInvalidPatchRequest() {
		return RequestDTO.builder().coords(buildCoordinates(1, 2)).roomSize(buildCoordinates(5, 5))
				.patches(buildInvalidPatches()).instructions("NNESEESWNWW").build();
	}

	public static RequestDTO buildWithOneInvalidPatchRequest() {
		return RequestDTO.builder().coords(buildCoordinates(1, 2)).roomSize(buildCoordinates(5, 5))
				.patches(buildWithOneInvalidPatch()).instructions("NNESEESWNWW").build();
	}

	public static Coordinates buildCoordinates(final Integer x, final Integer y) {
		return Coordinates.builder().x(x).y(y).build();
	}

	public static List<Coordinates> buildPatches() {
		return Arrays.asList(buildCoordinates(1, 0), buildCoordinates(2, 2), buildCoordinates(2, 3));
	}

	public static List<Coordinates> buildInvalidPatches() {
		return Arrays.asList(buildCoordinates(0, 0), buildCoordinates(-1, -4), buildCoordinates(6, 6));
	}

	public static List<Coordinates> buildWithOneInvalidPatch() {
		return Arrays.asList(buildCoordinates(1, 0), buildCoordinates(2, 2), buildCoordinates(6, 6));
	}
}
