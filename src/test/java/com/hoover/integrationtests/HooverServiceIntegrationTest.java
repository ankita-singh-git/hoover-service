package com.hoover.integrationtests;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.hoover.HooverServiceApplication;
import com.hoover.dto.RequestDTO;
import com.hoover.util.TestData;

@SpringBootTest(classes = HooverServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HooverServiceIntegrationTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void shouldReturnValidResponseForValidRequest() throws JSONException {
		String response = postNavigateAndCleanRequest(TestData.buildValidRequest());
		JSONAssert.assertEquals(getResourceAsString("integration/successResponse.json"), response, false);
	}

	@Test
	public void shouldReturnExceptionForInvalidStartCoords() throws JSONException {
		RequestDTO requestDTO = TestData.buildValidRequest();
		requestDTO.setCoords(TestData.buildCoordinates(-1, -1));
		
		String response = postNavigateAndCleanRequest(requestDTO);
		JSONAssert.assertEquals(getResourceAsString("integration/errorResponseInvalidStartCoords.json"), response, false);
	}
	
	@Test
	public void shouldReturnExceptionForInvalidRoomsize() throws JSONException {
		RequestDTO requestDTO = TestData.buildValidRequest();
		requestDTO.setRoomSize(TestData.buildCoordinates(2, 5));
		
		String response = postNavigateAndCleanRequest(requestDTO);
		JSONAssert.assertEquals(getResourceAsString("integration/errorResponseInvalidRoomSize.json"), response, false);
	}
	
	@Test
	public void shouldReturnExceptionForInvalidPatches() throws JSONException {
		String response = postNavigateAndCleanRequest(TestData.buildInvalidPatchRequest());
		JSONAssert.assertEquals(getResourceAsString("integration/errorResponseInvalidPatches.json"), response, false);
	}

	private String postNavigateAndCleanRequest(RequestDTO request) {
		return new String(Objects.requireNonNull(webTestClient.post().uri("/hoover-service/v1/clean")
				.accept(APPLICATION_JSON).body(BodyInserters.fromObject(request)).exchange().expectBody().returnResult()
				.getResponseBody()));
	}

	private String getResourceAsString(final String resourcePath) {
		try {
			return new String(getClass().getClassLoader().getResourceAsStream(resourcePath).readAllBytes(),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("File not found");
		}
	}

}
