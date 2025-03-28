package api_tests;

import org.hamcrest.Matchers;

import api.mock.WireMockStubManager;
import api.utils.APIUtils;
import io.restassured.response.Response;

public class WireMockTest {
    public static void main(String[] args) {
        System.out.println("🚀 Starting WireMock Test Runner...\n");

        // 🔹 1. Setup WireMock Stubs
        WireMockStubManager.setupStubs();
        System.out.println("✅ WireMock stubs initialized.\n");

        // 🔹 2. Define Variables
        String username = "admin";
        String password = "2AqjN!lC2!Vy";
        String endpoint = "http://localhost:8080/api/now/table/change_request";

        // 🔹 3. Valid Test Case (201 Created)
        System.out.println("🟢 Running Valid POST Test...");
        
        String validBody = "{ \"short_description\": \"My laptop problem\" }";
        
        Response validResponse = 
        		APIUtils.sendPostWithBasicAuth(endpoint, username, password, validBody);
        printResponse(validResponse);
        
        // Missing Credentials
        String endpoint1 = "http://localhost:8080/api/now/table/1";
        Response missingResponse = APIUtils.sendPostWithBasicAuth(endpoint1, username, password, validBody);
        printResponse(missingResponse);
		
        System.out.println("\n🚀 WireMock Test Runner Execution Completed!");
    }

    // 🔹 Utility Method to Print Response Details
    private static void printResponse(Response response) 
    {
        System.out.println("🔍 Response Code: " + response.getStatusCode());
        System.out.println("📄 Response Body: \n" );
        System.out.println("----------------------------\n");
    }
    public static void validateStatusCode(Response response, int expected, String status)
	{
		response.then().assertThat().statusCode(Matchers.equalTo(expected));
		System.out.println(response.getStatusCode() + ": " + status);
	}
}
