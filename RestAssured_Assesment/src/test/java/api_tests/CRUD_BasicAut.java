package api_tests;

import org.hamcrest.Matchers;

import api.config.ConfigManager;
import api.utils.APIUtils;
import io.restassured.response.Response;

public class CRUD_BasicAut {
	
	static String tablename = ConfigManager.getProperty("tablename");
	static String username = ConfigManager.getProperty("username");
	static String password = ConfigManager.getProperty("password");
	static String incidentID;
	String status;
	Response response;
	
	public static void main(String[] args) {

		CRUD_BasicAut basicIncident = new CRUD_BasicAut();
		basicIncident.postIncident();
		basicIncident.getIncident();
		basicIncident.putIncident();
		basicIncident.deleteIncident();
	}
	public void postIncident() 
	{
		// Create Incident
		String body = "{\r\n" + "    \"short_description\": \"My laptop problem\"\r\n" + "}";
		String status = "Created";
		
		response = APIUtils.sendPostWithBasicAuth(tablename, username, password, body);
		System.out.println("🟢  POST Response: 🟢");
		//response.prettyPrint();
		incidentID = response.jsonPath().getString("result.sys_id");
		validateStatusCode(response, 201, status);
	}
	public void getIncident() 
	{
		// Read Incident
		String status = "OK";
		response = APIUtils.sendGetWithBasicAuth(tablename+ "/" + incidentID, username, password);
		System.out.println("🟢  GET Response: 🟢");
		//response.prettyPrint();
		validateStatusCode(response, 200, status);
	}
	public void putIncident()
	{
		// Update Incident
		String status = "OK";
		String body = "{\r\n" + "    \"short_description\": \"My laptop problem is fixed\"\r\n" + "}";
		response = APIUtils.sendPutWithBasicAuth(tablename + "/" + incidentID, username, password, body);
		System.out.println("🟢  PUT Response: 🟢");
		//response.prettyPrint();
		validateStatusCode(response, 200, status);
	}
	public void deleteIncident()
	{
		status = "No Content";
		response = APIUtils.sendDeleteWithBasicAuth(tablename+ "/" + incidentID, username, password);
		System.out.println("🟢  Delete Response: 🟢");
		//response.prettyPrint();
		validateStatusCode(response, 204, status);
	}
	public void validateStatusCode(Response response, int expected, String status)
	{
		response.then().assertThat().statusCode(Matchers.equalTo(expected));
		System.out.println(response.getStatusCode() + ": " + status);
	}

}
