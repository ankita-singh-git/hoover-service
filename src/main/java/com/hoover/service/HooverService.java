package com.hoover.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.hoover.dto.RequestDTO;
import com.hoover.dto.ResponseDTO;
import com.hoover.exception.ApplicationException;
import com.hoover.util.Constants;
import com.hoover.util.DirectionsEnum;

/**
 * HooverService .java - This is service class
 * 
 * @author Ankita Singh
 * @version 1.0
 */
@Service
public class HooverService {

	private static final Logger LOGGER = Logger.getLogger(HooverService.class.getName());

	/**
	 * Calculates coordinates based on directions provided. Check if the calculated
	 * coordinates is valid Remove dirt patches if dirt coordinates matches with
	 * coordinate calculated and populates response object
	 * 
	 * @return A ResponseDTO object.
	 * @throws ApplicationException
	 */
	public ResponseDTO navigateAndClean(RequestDTO request) throws ApplicationException {
		LOGGER.info("Inside navigateAndClean method " + LOGGER.getName());

		ResponseDTO response = null;

		if (isValidRequest(request)) {
			int[] coords = request.getCoords();
			List<int[]> patches = request.getPatches();
			List<int[]> patchesRemoved = new ArrayList<int[]>(patches.size());

			Stream<Character> charactersStream = request.getInstructions().chars().mapToObj(c -> (char) c);
			charactersStream.forEach(character -> performOperations(character, request, patchesRemoved));

			patches.removeAll(patchesRemoved);
			response = new ResponseDTO(coords, patchesRemoved.size());
		}
		return response;

	}

	// Validation methods
	/**
	 * Check if the request is valid
	 * 
	 * @throws ApplicationException
	 * 
	 */
	private boolean isValidRequest(RequestDTO request) throws ApplicationException {
		if (!isValueValid(request.getCoords(), request.getRoomSize()) || isValueValid(request.getRoomSize()) == null
				|| request.getInstructions() == null || !isValueValid(request.getPatches(), request.getRoomSize())) {
			throw new ApplicationException(Constants.INVALID_REQUEST_MESSAGE);
		}
		return true;
	}

	/**
	 * Check if provided coordinates values are valid
	 * 
	 * @return false if invalid
	 * 
	 */
	private Boolean isValueValid(int[] coords) {
		if (coords == null || coords.length != 2 || coords[0] < 0 || coords[1] < 0) {
			return false;
		}
		return true;
	}

	/**
	 * Check if the calculated coordinates are valid based on room size
	 * 
	 * @throws ApplicationException
	 * 
	 */
	private Boolean isValueValid(int[] coords, int[] roomSize) throws ApplicationException {
		if (!isValueValid(coords)) {
			throw new ApplicationException(Constants.INVALID_REQUEST_MESSAGE);
		} else if (coords[0] > roomSize[0] || coords[1] > roomSize[1]) {
			LOGGER.warning(
					"Coordinates " + coords[0] + coords[1] + " exceeds the room size " + roomSize[0] + roomSize[1]);
			throw new ApplicationException(Constants.INVALID_COORDS);
		}
		return true;
	}

	/**
	 * Check if provided patches are valid for room size
	 * 
	 * @return false if invalid
	 * 
	 */
	private boolean isValueValid(List<int[]> patches, int[] roomSize) throws ApplicationException {
		for (int[] patch : patches) {
			if (!isValueValid(patch, roomSize))
				return false;
		}
		return true;
	}

	// Validation methods ends

	/**
	 * perform operations like navigate and clean patches
	 * 
	 * @throws ApplicationException
	 * 
	 */
	public void performOperations(Character character, RequestDTO request, List<int[]> patchesRemoved)
			throws ApplicationException {
		navigate(request.getCoords(), character);
		if (isValueValid(request.getCoords(), request.getRoomSize())) {
			cleanPatches(request.getCoords(), request.getPatches(), patchesRemoved);
		}
	}

	/**
	 * Calculates coordinates based on directions provided.
	 * 
	 * @throws ApplicationException
	 **/
	public void navigate(int[] coords, char instruction) throws ApplicationException {
		LOGGER.info("Navigating:: current coords" + coords[0] + "," + coords[1] + " direction " + instruction);
		Character direction = Character.toUpperCase(instruction);
		if (direction == DirectionsEnum.North.asChar())
			coords[1] = coords[1] + 1;
		else if (direction == DirectionsEnum.East.asChar())
			coords[0] = coords[0] + 1;
		else if (direction == DirectionsEnum.South.asChar())
			coords[1] = coords[1] - 1;
		else if (direction == DirectionsEnum.West.asChar())
			coords[0] = coords[0] - 1;
		else {
			LOGGER.warning("Invalid Direction " + instruction);
			throw new ApplicationException(Constants.INVALID_DIRECTIONS);
		}
		LOGGER.info("Navigated to coords" + coords[0] + "," + coords[1]);

	}

	/**
	 * Removes dirt patches if dirt coordinates matches with coordinates calculated
	 * 
	 * @throws ApplicationException
	 * 
	 */
	public void cleanPatches(int[] coords, List<int[]> patches, List<int[]> patchesRemoved)
			throws ApplicationException {
		int[] patch = patches.stream().filter(p -> p[0] == coords[0] && p[1] == coords[1]).findAny().orElse(null);

		if (patch != null && !patchesRemoved.contains(patch)) {
			patchesRemoved.add(patch);
			LOGGER.info("Cleaning patch " + patch[0] + "," + patch[1]);
		}
	}

}
