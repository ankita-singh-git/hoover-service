package com.hoover.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoover.dto.Coordinates;
import com.hoover.dto.RequestDTO;
import com.hoover.dto.ResponseDTO;
import com.hoover.exception.ValidationException;
import com.hoover.util.DirectionsEnum;

/**
 * HooverService .java - This is service class
 * 
 * @author Ankita Singh
 * @version 1.0
 */
@Service
public class HooverServiceImpl implements HooverService {

	private static final Logger LOGGER = Logger.getLogger(HooverServiceImpl.class.getName());

	private ValidationService validationService;

	@Autowired
	public HooverServiceImpl(final ValidationService validationService) {
		this.validationService = validationService;
	}

	@Override
	public ResponseDTO navigateAndClean(final RequestDTO request) throws ValidationException {
		LOGGER.info("Inside navigateAndClean method " + LOGGER.getName());
		return validationService.validateRequest(request).filter(Boolean::booleanValue).map(b -> performAction(request))
				.orElse(null);
	}

	private ResponseDTO performAction(final RequestDTO request) {
		List<Coordinates> patches = request.getPatches();
		List<Coordinates> patchesRemoved = new ArrayList<Coordinates>(patches.size());

		request.getInstructions().chars().mapToObj(c -> (char) c)
				.forEach(direction -> navigateAndCleanPatches(direction, request, patchesRemoved));

		return new ResponseDTO(request.getCoords(), patchesRemoved.size());
	}

	private void navigateAndCleanPatches(final Character direction, final RequestDTO request,
			final List<Coordinates> patchesRemoved) throws ValidationException {
		navigate(request.getCoords(), direction);
		if (validationService.isCoordsValid(request.getCoords(), request.getRoomSize())) { // checking if calculated
			cleanPatches(request, patchesRemoved);
		} else {
			LOGGER.severe("Calculated coordinates exceeded the room size");
			throw new ValidationException("Calculated coordinates exceeded the room size ");
		}
	}

	private void navigate(final Coordinates coords, final Character instruction) throws ValidationException {
		DirectionsEnum directionEnum = DirectionsEnum.getEnumBy(instruction);
		if (Objects.isNull(directionEnum)) {
			LOGGER.severe("Invalid Direction " + instruction);
			throw new ValidationException("Invalid Direction Provided. Accepted values are N, S, E, W");
		}
		switch (DirectionsEnum.getEnumBy(instruction)) {
		case North:
			coords.setY(coords.getY() + 1);
			break;
		case East:
			coords.setX(coords.getX() + 1);
			break;
		case South:
			coords.setY(coords.getY() - 1);
			break;
		case West:
			coords.setX(coords.getX() - 1);
			break;
		}
	}

	private void cleanPatches(final RequestDTO request, final List<Coordinates> patchesRemoved)
			throws ValidationException {
		request.getPatches().forEach(patch -> cleanPatch(request, patchesRemoved, patch));
	}

	private void cleanPatch(final RequestDTO request, final List<Coordinates> patchesRemoved, Coordinates patch) {
		if (patch.getX() == request.getCoords().getX() && patch.getY() == request.getCoords().getY()
				&& !patchesRemoved.contains(patch)) {
			patchesRemoved.add(patch);
		}
	}

}
