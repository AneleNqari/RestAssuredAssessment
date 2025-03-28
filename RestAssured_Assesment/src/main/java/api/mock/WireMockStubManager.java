package api.mock;

import com.github.tomakehurst.wiremock.client.WireMock;

import api.config.ConfigManager;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockStubManager {

	static String tablename = ConfigManager.getProperty("tablename");
	
	public static void setupStubs() {
		// Mock response body for successful request
		String successBody = "{\r\n" + "    \"result\": {\r\n"
				+ "        \"sys_id\": \"f23867c6c33f56105119bd33e40131be\",\r\n"
				+ "        \"number\": \"INC0011175\",\r\n"
				+ "        \"short_description\": \"My laptop problem\",\r\n"
				+ "        \"description\": \"battery backUp\"\r\n" + "    }\r\n" + "}";
		// 🔹 1. 201 - Successful Request (Correct Authorization & Data)

		WireMock.stubFor(WireMock.post("/api/now/table/" + tablename)
				.withHeader("Authorization", WireMock.equalTo("Basic YWRtaW46MkFxak4hbEMyIVZ5"))
				.withRequestBody(matchingJsonPath("$.short_description", WireMock.notMatching("")))
				.willReturn(WireMock.aResponse().withStatus(201).withBody(successBody)));

		WireMock.stubFor(WireMock.post("/api/now/table/incident").withHeader("Authorization", WireMock.absent())
				.willReturn(WireMock.aResponse().withStatus(401).withBody("{\"error\":\"Unauthorized access\"}")));


		
		  // 🔹 2. 401 - Unauthorized Access (Missing Authorization)
		  WireMock.stubFor(WireMock.post("/api/now/table/" + tablename)
		  .withHeader("Authorization", WireMock.absent())
		  .willReturn(WireMock.aResponse() .withStatus(401)
		  .withBody("{\"error\":\"Unauthorized access\"}")));
		  
		  // 🔹 3. 403 - Forbidden (Invalid Authorization)
		  WireMock.stubFor(WireMock.post("/api/now/table/" + tablename)
		  .withHeader("Authorization", WireMock.equalTo("Basic InvalidToken123"))
		  .willReturn(WireMock.aResponse() .withStatus(403)
		  .withBody("{\"error\":\"Forbidden: Access Denied\"}")));
		  
		  // 🔹 4. 400 - Bad Request (Invalid Input Data)
		  WireMock.stubFor(WireMock.post("/api/now/table/" + tablename)
		  .withRequestBody(matchingJsonPath("$.short_description", absent()))
		  .willReturn(WireMock.aResponse() .withStatus(400)
		  .withBody("{\"error\":\"Bad Request: 'short_description' is required\"}")));
		  
		  // 🔹 5. 500 - Internal Server Error (Unexpected System Issue)
		  WireMock.stubFor(WireMock.post("/api/now/table/" + tablename)
		  .withRequestBody(matchingJsonPath("$.causeError", equalTo("true")))
		  .willReturn(WireMock.aResponse() .withStatus(500)
		  .withBody("{\"error\":\"Internal Server Error: Unexpected issue occurred\"}")
		  ));
		 

	}
}
