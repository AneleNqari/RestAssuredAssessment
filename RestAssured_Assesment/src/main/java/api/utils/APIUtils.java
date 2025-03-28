package api.utils;

import api.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class APIUtils {

    // 🔹 POST Request (OAuth 2.0)
	
	static {
        // Set the Base URI dynamically from properties
        RestAssured.baseURI = ConfigManager.getProperty("protocol") + "://" 
                            + ConfigManager.getProperty("hostname") 
                            + ConfigManager.getProperty("basepath");
    }


    public static Response sendPostWithOAuth(String endpoint, String token, String body) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .post(endpoint);
    }

    // 🔹 POST Request (Basic Auth)
    public static Response sendPostWithBasicAuth(String endpoint, String username, String password, String body) {
        return RestAssured.given()
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON)
                .body(body)
                .post(endpoint);
    }

    // 🔹 GET Request (OAuth 2.0)
    public static Response sendGetWithOAuth(String endpoint, String token) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .get(endpoint);
    }

    // 🔹 GET Request (Basic Auth)
    public static Response sendGetWithBasicAuth(String endpoint, String username, String password) {
        return RestAssured.given()
                .auth().preemptive().basic(username, password)
                .get(endpoint);
    }

    // 🔹 PUT Request (OAuth 2.0)
    public static Response sendPutWithOAuth(String endpoint, String token, String body) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .put(endpoint);
    }

    // 🔹 PUT Request (Basic Auth)
    public static Response sendPutWithBasicAuth(String endpoint, String username, String password, String body) {
        return RestAssured.given()
                .auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON)
                .body(body)
                .put(endpoint);
    }

    // 🔹 DELETE Request (OAuth 2.0)
    public static Response sendDeleteWithOAuth(String endpoint, String token) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .delete(endpoint);
    }

    // 🔹 DELETE Request (Basic Auth)
    public static Response sendDeleteWithBasicAuth(String endpoint, String username, String password) {
        return RestAssured.given()
                .auth().preemptive().basic(username, password)
                .delete(endpoint);
    }
}
