package api.utils;

import java.util.HashMap;
import java.util.Map;

import api.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OAuthUtils {

	public static String getOAuthToken() {
		
		Map<String, String> formParams = new HashMap<>();
		formParams.put("grant_type", "password");
		formParams.put("client_id", ConfigManager.getProperty("client_id"));
		formParams.put("client_secret", ConfigManager.getProperty("client_secret"));
		formParams.put("username", ConfigManager.getProperty("username"));
		formParams.put("password", ConfigManager.getProperty("password"));

		Response response = RestAssured.given().formParams(formParams).post(ConfigManager.getProperty("token_url"));

		// Modify the code below to extract the access_token from the response
		return response.jsonPath().getString("access_token");
	}
}
