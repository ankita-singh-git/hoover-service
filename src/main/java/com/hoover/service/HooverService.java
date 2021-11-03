package com.hoover.service;

import com.hoover.dto.RequestDTO;
import com.hoover.dto.ResponseDTO;
import com.hoover.exception.ValidationException;

public interface HooverService {

	/**
	 * Calculates coordinates based on directions provided. Check if the calculated
	 * coordinates is valid Remove dirt patches if dirt coordinates matches with
	 * coordinate calculated and populates response object
	 * 
	 * @return A ResponseDTO object.
	 * @throws ApplicationException
	 */
	ResponseDTO navigateAndClean(RequestDTO request) throws ValidationException;

}
