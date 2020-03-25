package com.hoover.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * RequestDTO .java - This is request POJO class for JSON
 * 
 * @author Ankita Singh
 * @version 1.0
 */
public class RequestDTO {
	@NotNull(message = "Room size cannot be null or empty")
	@Size(max = 2, min = 2, message = "x and y coordinates should be provided")
	private int[] roomSize;
	@NotNull(message = "Coordinates cannot be null or empty")
	@Size(max = 2, min = 2, message = "x and y coordinates should be provided")
	private int[] coords;
	@NotNull(message = "Patches cannot be null or empty")
	private List<int[]> patches;
	@NotNull(message = "Instructions cannot be null or empty")
	private String instructions;

	public int[] getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(int[] roomSize) {
		this.roomSize = roomSize;
	}

	public int[] getCoords() {
		return coords;
	}

	public void setCoords(int[] coords) {
		this.coords = coords;
	}

	public List<int[]> getPatches() {
		return patches;
	}

	public void setPatches(List<int[]> patches) {
		this.patches = patches;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

}
