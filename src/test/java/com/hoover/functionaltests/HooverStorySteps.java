package com.hoover.functionaltests;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.apache.http.client.ClientProtocolException;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.github.tomakehurst.wiremock.WireMockServer;

public class HooverStorySteps extends Steps {

	private TestRestTemplate restTemplate = new TestRestTemplate();

	private HttpEntity<String> entity;

	private ResponseEntity<String> response;

	private WireMockServer wiremockServer;

	@BeforeStories
	public void configureWiremock() {
		wiremockServer = new WireMockServer();
		wiremockServer.start();
	}

	@AfterStories
	public void stopWiremock() {
		Optional.ofNullable(wiremockServer).ifPresent(WireMockServer::stop);
	}

	@Given("a request to clean patches")
	public void givenARequestToCleanPatches() throws UnsupportedEncodingException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", APPLICATION_JSON_VALUE);
		headers.set("Accept", APPLICATION_JSON_VALUE);
		entity = new HttpEntity<String>(getResourceAsString("data/request.json"), headers);
	}

	@Given("the request is stubbed")
	public void givenAMockCall() {
		wiremockServer.stubFor(
				post(urlEqualTo("/hoover-service/v1/clean")).withHeader("Content-Type", equalTo(APPLICATION_JSON_VALUE))
						.withRequestBody(equalToJson(getResourceAsString("data/request.json")))
						.willReturn(ok().withHeader("Content-Type", APPLICATION_JSON_VALUE)
								.withBody(getResourceAsString("data/response.json"))));
	}

	@When("a navigate and clean request is recieved")
	public void whenNavigateRequestIsRecieved() throws ClientProtocolException, IOException {
		response = restTemplate.exchange("http://localhost:8080/hoover-service/v1/clean", HttpMethod.POST, entity,
				String.class);
	}

	@Then("a response with final hoover position and number of patches cleaned in returned")
	public void returnResponse() throws UnsupportedOperationException, JSONException, IOException {
		JSONAssert.assertEquals(getResourceAsString("data/response.json"), response.getBody(), false);
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