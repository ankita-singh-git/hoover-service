package com.hoover.service;

import java.util.Optional;

import com.hoover.dto.Coordinates;
import com.hoover.dto.RequestDTO;
import com.hoover.exception.ValidationException;

public interface ValidationService {
	
	public Optional<Boolean> validateRequest(final RequestDTO request);

	public Boolean isCoordsValid(final Coordinates coords, final Coordinates roomsize) throws ValidationException;

}
