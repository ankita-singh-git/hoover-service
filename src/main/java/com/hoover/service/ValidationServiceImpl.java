package com.hoover.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hoover.dto.Coordinates;
import com.hoover.dto.RequestDTO;
import com.hoover.exception.ValidationException;

/**
 * ValidationService .java - This has methods for performing validations
 * 
 * @author Ankita Singh
 * @version 1.0
 */
@Service
public class ValidationServiceImpl implements ValidationService {

	private static final Logger LOGGER = Logger.getLogger(HooverServiceImpl.class.getName());

	@Override
	public Optional<Boolean> validateRequest(final RequestDTO request) {
		LOGGER.info("Validating request");
		return Optional.of(isRoomSizeValid(request.getRoomSize()) && isProvidedStartCoordsValid(request)
				&& isPatchesValid(request));
	}

	@Override
	public Boolean isCoordsValid(final Coordinates coords, final Coordinates roomsize) throws ValidationException {
		return isCoordsValid(coords) && (coords.getX() <= roomsize.getX()) && (coords.getY() <= roomsize.getY());
	}

	private boolean isRoomSizeValid(final Coordinates roomSize) {
		if (isCoordsValid(roomSize) && (roomSize.getX() == roomSize.getY())) {
			return true;
		}
		throw new ValidationException("Room size provided is invalid");
	}

	private Boolean isProvidedStartCoordsValid(final RequestDTO request) {
		if (isCoordsValid(request.getCoords(), request.getRoomSize())) {
			return true;
		}
		throw new ValidationException("Provided start coords are not valid");
	}

	private boolean isPatchesValid(final RequestDTO request) {
		if (!checkIfInvalidCoordsPresent(request).get().isEmpty()) {
			throw new ValidationException("Invalid patches present");
		}
		return true;
	}

	private Optional<List<Coordinates>> checkIfInvalidCoordsPresent(final RequestDTO request) {
		return Optional.ofNullable(request.getPatches().stream()
				.filter(patch -> !isCoordsValid(patch, request.getRoomSize())).collect(Collectors.toList()));
	}

	private boolean isCoordsValid(final Coordinates coords) {
		return Objects.nonNull(coords) && coords.getX() >= 0 && coords.getY() >= 0;
	}
}
