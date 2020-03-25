package com.hoover.dto;

/**  
*ResponseDTO .java - This is response POJO class for JSON
* @author  Ankita Singh
* @version 1.0 
*/ 
public class ResponseDTO {
	private int[] coords = new int[2];
	private int patches;

	public ResponseDTO(int[] coords, int patches) {
		super();
		this.coords = coords;
		this.patches = patches;
	}

	public int[] getCoords() {
		return coords;
	}

	public void setCoords(int[] coords) {
		this.coords = coords;
	}

	public int getPatches() {
		return patches;
	}

	public void setPatches(int patches) {
		this.patches = patches;
	}
}
