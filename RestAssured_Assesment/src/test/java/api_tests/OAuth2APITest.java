package api_tests;

import api.utils.APIUtils;
import api.utils.OAuthUtils;

import org.hamcrest.Matchers;

import api.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OAuth2APITest {
	
	Response response;
	static String tablename = ConfigManager.getProperty("tablename");
	static String username = ConfigManager.getProperty("username");
	static String password = ConfigManager.getProperty("password");
	String status;
	static String incidentID;
	
	public static void main(String[] args) {
		// 🔹 Get API Base URL from properties

		RestAssured.baseURI = ConfigManager.getProperty("protocol") + "://" + 
				ConfigManager.getProperty("hostname")
				+ ConfigManager.getProperty("basepath");

		// 🔹 Get OAuth Token
		String token = OAuthUtils.getOAuthToken();
		System.out.println("🟢 OAuth Token: " + token);
		
		OAuth2APITest tokenIncident = new OAuth2APITest();
		tokenIncident.postWithToken(token);
		tokenIncident.getWithToken(token);
		tokenIncident.putWithToken(token);
		tokenIncident.deleteWithToken(token);
		

	}
	public void postWithToken(String t)
	{
	// 🔹 Create Resource (POST)
		String requestBody="{\r\n"
				+ "    \"short_description\": \"My laptop problem\"\r\n"
				+ "}";
		status = "Created"; 
		response = APIUtils.sendPostWithOAuth(tablename, t, requestBody);
		System.out.println("🟢 POST Response: " );
		//response.prettyPrint();
		incidentID = response.jsonPath().getString("result.sys_id");
		validateStatusCode(response, 201, status);
		System.out.println("=======================================");
		System.out.println("🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢");
	}
	public void getWithToken(String t)
	{
		status = "OK";
		response = APIUtils.sendGetWithOAuth(tablename + "/" + incidentID, t);
		System.out.println("🟢 GET Response: " );
		//response.prettyPrint();
		validateStatusCode(response, 200, status);
		System.out.println("🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢");
	}
	public void putWithToken(String t)
	{
	// 🔹 Create Resource (POST)
		String requestBody="{\r\n"
				+ "    \"short_description\": \"My laptop problem is fixed now\"\r\n"
				+ "}";
		status = "OK";
		response = APIUtils.sendPutWithOAuth(tablename + "/" + incidentID, t, requestBody);
		System.out.println("🟢 PUT Response: " );
		//response.prettyPrint();
		validateStatusCode(response, 200, status);
		System.out.println("🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢");
	}
	public void deleteWithToken(String t)
	{
		status = "No Content";
		response = APIUtils.sendDeleteWithOAuth(tablename + "/" + incidentID, t);
		System.out.println("🟢 Delete Response: " );
		//response.prettyPrint();
		validateStatusCode(response, 204, status);
		System.out.println("🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢 🟢");
	}
	public void validateStatusCode(Response response, int expected, String status)
	{
		response.then().assertThat().statusCode(Matchers.equalTo(expected));
		System.out.println(response.getStatusCode() + ": " + status);
	}
}
