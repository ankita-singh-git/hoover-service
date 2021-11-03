package com.hoover.util;

import java.util.Arrays;

public enum DirectionsEnum {
	North('N'), South('S'), West('W'), East('E');

	private Character direction;

	DirectionsEnum(Character direction) {
		this.direction = direction;
	}

	public Character getDirection() {
		return this.direction;
	}

	public static DirectionsEnum getEnumBy(final Character direction) {
		return Arrays.stream(DirectionsEnum.values()).filter(d -> d.getDirection().equals(direction)).findFirst()
				.orElse(null);
	}
}
