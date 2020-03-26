package com.hoover.util;

public enum DirectionsEnum {
	 North('N'), South('S'), West('W'), East('E');

    public char asChar() {
        return asChar;
    }

    private final char asChar;

    DirectionsEnum(char asChar) {
        this.asChar = asChar;
    }
	
	@Override
	public String toString() {
		return name();
	}
}
