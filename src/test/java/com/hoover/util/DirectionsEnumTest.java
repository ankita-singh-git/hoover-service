package com.hoover.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class DirectionsEnumTest {
	
	@Test
	public void shouldReturnEnumByCharater() {
		assertEquals(DirectionsEnum.North, DirectionsEnum.getEnumBy('N'));
	}	
	
	@Test
	public void shouldReturnNullIfInvalid() {
		assertEquals(null, DirectionsEnum.getEnumBy('K'));
	}
}
