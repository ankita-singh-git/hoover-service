package com.hoover.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coordinates {
	private int x;
	private int y;
}
