package com.hoover;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
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
import com.hoover.dto.ResponseDTO;
import com.hoover.exception.ApplicationException;
import com.hoover.service.HooverService;
import com.hoover.util.Constants;
import com.hoover.util.DirectionsEnum;

@SpringBootTest
@AutoConfigureMockMvc
class HooverApplicationTests {

	@Autowired
	private MockMvc mvc;

	//Test cases for service
	
	@Test
	public void When_InvalidRequest_ServiceReturnsError() throws Exception {
		RequestDTO request = new RequestDTO();
		HooverService service = new HooverService();
		ApplicationException exception = Assertions.assertThrows(ApplicationException.class,
				() -> service.navigateAndClean(request));
		assertTrue(exception.getMessage().contains(Constants.INVALID_REQUEST_MESSAGE));
	}
	@Test
	public void When_ValidCoordsAndDirections_Navigate() throws Exception {
		int[] coords = new int[] { 1, 2 };
		Character instruction = DirectionsEnum.North.asChar();

		HooverService service = new HooverService();
		service.navigate(coords, instruction);

		assertTrue(coords[0] == 1);
		assertTrue(coords[1] == 3);
	}
	
	@Test
	public void When_InvalidDirections_Navigate() throws Exception {
		int[] coords = new int[] { 1, 2 };
		Character instruction = 'A';

		HooverService service = new HooverService();

		ApplicationException exception = Assertions.assertThrows(ApplicationException.class,
				() -> service.navigate(coords, instruction));
		assertTrue(exception.getMessage().contains(Constants.INVALID_DIRECTIONS));
		
	}

	@Test
	public void When_ValidCoords_CleanPatches() throws Exception {
		int[] coords = new int[] { 1, 2 };
		List<int[]> patches = new ArrayList<int[]>();
		patches.add(new int[] { 1, 2 });
		patches.add(new int[] { 2, 2 });
		patches.add(new int[] { 2, 3 });

		List<int[]> patchesRemoved = new ArrayList<int[]>();

		HooverService service = new HooverService();
		service.cleanPatches(coords, patches, patchesRemoved);

		assertTrue(patchesRemoved.size() == 1);
	}
	
	
	@Test
	public void When_CoordsMissing_ServiceReturnsError() throws Exception {
		RequestDTO request = new RequestDTO();
		request.setRoomSize(new int[] { 5, 5 });
		request.setInstructions("NNESEESWNWW");
		List<int[]> patches = new ArrayList<int[]>();
		patches.add(new int[] { 1, 0 });
		patches.add(new int[] { 2, 2 });
		patches.add(new int[] { 2, 3 });
		request.setPatches(patches);

		HooverService service = new HooverService();
		ApplicationException exception = Assertions.assertThrows(ApplicationException.class,
				() -> service.navigateAndClean(request));
		assertTrue(exception.getMessage().contains(Constants.INVALID_REQUEST_MESSAGE));
	}

	@Test
	public void When_InvalidCoordsSize_ServiceReturnsError() throws Exception {
		RequestDTO request = new RequestDTO();
		request.setCoords(new int[] { 1, 2, 3 });
		request.setRoomSize(new int[] { 5, 5 });
		request.setInstructions("ABCD");
		List<int[]> patches = new ArrayList<int[]>();
		patches.add(new int[] { 1, 0 });
		patches.add(new int[] { 2, 2 });
		patches.add(new int[] { 2, 3 });
		request.setPatches(patches);

		HooverService service = new HooverService();
		ApplicationException exception = Assertions.assertThrows(ApplicationException.class,
				() -> service.navigateAndClean(request));
		assertTrue(exception.getMessage().contains(Constants.INVALID_REQUEST_MESSAGE));
	}
	
	@Test
	public void When_InvalidDirections_ServiceReturnsError() throws Exception {
		RequestDTO request = new RequestDTO();
		request.setCoords(new int[] { 1, 2 });
		request.setRoomSize(new int[] { 5, 5 });
		request.setInstructions("ABCD");
		List<int[]> patches = new ArrayList<int[]>();
		patches.add(new int[] { 1, 0 });
		patches.add(new int[] { 2, 2 });
		patches.add(new int[] { 2, 3 });
		request.setPatches(patches);

		HooverService service = new HooverService();
		ApplicationException exception = Assertions.assertThrows(ApplicationException.class,
				() -> service.navigateAndClean(request));
		assertTrue(exception.getMessage().contains(Constants.INVALID_DIRECTIONS));
	}
	
	@Test
	public void When_CoordsExceedsRoomSize_ServiceReturnsError() throws Exception {
		RequestDTO request = new RequestDTO();
		request.setCoords(new int[] { 5, 5 });
		request.setRoomSize(new int[] { 5, 5 });
		request.setInstructions("NWE");
		List<int[]> patches = new ArrayList<int[]>();
		patches.add(new int[] { 1, 0 });
		patches.add(new int[] { 2, 2 });
		patches.add(new int[] { 2, 3 });
		request.setPatches(patches);

		HooverService service = new HooverService();
		ApplicationException exception = Assertions.assertThrows(ApplicationException.class,
				() -> service.navigateAndClean(request));
		assertTrue(exception.getMessage().contains(Constants.INVALID_COORDS));
	}

	
	@Test
	public void When_ValidRequest_ServiceReturnsResponse() throws Exception {
		RequestDTO request = new RequestDTO();
		request.setCoords(new int[] { 1, 2 });
		request.setRoomSize(new int[] { 5, 5 });
		request.setInstructions("NNESEESWNWW");
		List<int[]> patches = new ArrayList<int[]>();
		patches.add(new int[] { 1, 0 });
		patches.add(new int[] { 2, 2 });
		patches.add(new int[] { 2, 3 });
		request.setPatches(patches);

		HooverService service = new HooverService();
		ResponseDTO dto = service.navigateAndClean(request);
		assertTrue(dto.getPatches() == 1);
		assertTrue(dto.getCoords()[0] == 1);
		assertTrue(dto.getCoords()[1] == 3);
	}

	//Test cases for API

	@Test
	public void When_InvalidRequest_ReturnError() throws Exception {
		String uri = "/hoover";
		RequestDTO request = new RequestDTO();

		String inputJson = mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}

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
