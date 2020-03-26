package com.hoover.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.hoover.dto.RequestDTO;
import com.hoover.dto.ResponseDTO;
import com.hoover.exception.ApplicationException;
import com.hoover.resource.HooverResource;
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
		int[] coords = request.getCoords();
		char[] instructions = request.getInstructions().toCharArray();
		List<int[]> patches = request.getPatches();
		List<int[]> patchesRemoved = new ArrayList<int[]>(patches.size());
		int[] roomSize = request.getRoomSize();

		if (isCoordsValid(coords, roomSize) && isPatchesValid(patches, roomSize)) { // checking if coords provided are
																					// valid
			for (char instruction : instructions) {
				navigate(coords, instruction);
				if (isCoordsValid(coords, roomSize)) { // checking if calculated coords are valid
					cleanPatches(coords, patches, patchesRemoved, roomSize);
				}
			}
			patches.removeAll(patchesRemoved);
			response = new ResponseDTO(coords, patchesRemoved.size());
		}
		return response;

	}

	/**
	 * Check if the calculated coordinates are valid
	 * 
	 * @throws ApplicationException
	 * 
	 */
	private Boolean isCoordsValid(int[] coords, int[] roomSize) throws ApplicationException {
		if (coords[0] < 0 || coords[1] < 0) {
			throw new ApplicationException("Coodinates provided are invalid");
		} else if (coords[0] > roomSize[0] || coords[1] > roomSize[1]) {
			LOGGER.warning("Calculated coordinates " + coords[0] + coords[1] + " exceeds the room size " + roomSize[0]
					+ roomSize[1]);
			throw new ApplicationException("Calculated coordinates exceeds the room size");
		}
		return true;
	}

	private boolean isPatchesValid(List<int[]> patches, int[] roomSize) throws ApplicationException {
		for (int[] patch : patches) {
			if (!isCoordsValid(patch, roomSize))
				return false;
		}
		return true;
	}

	/**
	 * Calculates coordinates based on directions provided.
	 * 
	 * @throws ApplicationException
	 **/
	private void navigate(int[] coords, char instruction) throws ApplicationException {
		if (Character.toUpperCase(instruction) == DirectionsEnum.North.asChar())
			coords[1] = coords[1] + 1;
		else if (Character.toUpperCase(instruction) == DirectionsEnum.East.asChar())
			coords[0] = coords[0] + 1;
		else if (Character.toUpperCase(instruction) == DirectionsEnum.South.asChar())
			coords[1] = coords[1] - 1;
		else if (Character.toUpperCase(instruction) == DirectionsEnum.West.asChar())
			coords[0] = coords[0] - 1;
		else {
			LOGGER.warning("Invalid Direction " + instruction);
			throw new ApplicationException("Invalid Direction Provided. Accepted values are N, S, E, W");
		}
	}

	/**
	 * Remove dirt patches if dirt coordinates matches with coordinate calculated
	 * 
	 * @throws ApplicationException
	 * 
	 */
	private void cleanPatches(int[] coords, List<int[]> patches, List<int[]> patchesRemoved, int[] roomSize)
			throws ApplicationException {
		for (int[] patch : patches) {
			if (patch[0] == coords[0] && patch[1] == coords[1]) {
				if (!patchesRemoved.contains(patch)) {
					patchesRemoved.add(patch);
				}
			}
		}
	}

}
