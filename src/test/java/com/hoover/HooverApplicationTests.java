package com.hoover;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoover.dto.RequestDTO;

@SpringBootTest
@AutoConfigureMockMvc
class HooverApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void When_ValidRequest_ReturnResponseObject() throws Exception {
		String uri = "/hoover";
		RequestDTO request = new RequestDTO();
		request.setCoords(new int[] { 1, 2 });
		request.setRoomSize(new int[] { 5, 5 });
		request.setInstructions("NNESEESWNWW");
		List<int[]> patches = new ArrayList<int[]>();
		patches.add(new int[] { 1, 0 });
		patches.add(new int[] { 2, 2 });
		patches.add(new int[] { 2, 3 });

		request.setPatches(patches);

		String inputJson = mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	public void When_CoordsNotProvided_ReturnError() throws Exception {
		String uri = "/hoover";
		RequestDTO request = new RequestDTO();
		request.setRoomSize(new int[] { 5, 5 });
		request.setInstructions("NNESEESWNWW");
		List<int[]> patches = new ArrayList<int[]>();
		patches.add(new int[] { 1, 0 });
		patches.add(new int[] { 2, 2 });
		patches.add(new int[] { 2, 3 });

		request.setPatches(patches);

		String inputJson = mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}

	@Test
	public void When_RoomsizeNotProvided_ReturnError() throws Exception {
		String uri = "/hoover";
		RequestDTO request = new RequestDTO();
		request.setCoords(new int[] { 1, 2 });
		request.setInstructions("NNESEESWNWW");
		List<int[]> patches = new ArrayList<int[]>();
		patches.add(new int[] { 1, 0 });
		patches.add(new int[] { 2, 2 });
		patches.add(new int[] { 2, 3 });

		request.setPatches(patches);

		String inputJson = mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}

	@Test
	public void When_InvalidDirectionsProvided_ReturnError() throws Exception {
		String uri = "/hoover";
		RequestDTO request = new RequestDTO();
		request.setCoords(new int[] { 1, 2 });
		request.setRoomSize(new int[] { 5, 5 });
		request.setInstructions("ABB");
		List<int[]> patches = new ArrayList<int[]>();
		patches.add(new int[] { 1, 0 });
		patches.add(new int[] { 2, 2 });
		patches.add(new int[] { 2, 3 });

		request.setPatches(patches);

		String inputJson = mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}

	@Test
	public void When_InvalidPatchesCoordsProvided_ReturnError() throws Exception {
		String uri = "/hoover";
		RequestDTO request = new RequestDTO();
		request.setCoords(new int[] { 1, 2 });
		request.setRoomSize(new int[] { 5, 5 });
		request.setInstructions("NNESEESWNWW");
		List<int[]> patches = new ArrayList<int[]>();
		patches.add(new int[] { -1, 0 });
		patches.add(new int[] { -2, 2 });
		patches.add(new int[] { 2, 3 });

		request.setPatches(patches);

		String inputJson = mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
