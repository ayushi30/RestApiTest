package com.rest.testing;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;

public class StudentApiAutomation {
	RequestSpecification request;
	
    @BeforeClass
	public void setReastAssuredConfigParams() {
		baseURI = "http://localhost:8080";
		basePath = "/students/";	
	}

	@BeforeMethod
	public void setRestAssuredRequestSpec() {
		request = given().baseUri(baseURI).basePath(basePath).contentType(ContentType.JSON);
	}
	
	@Test
	public void createStudentDetailsTest() {	
		
		JsonObject requestParams = new JsonObject();
		requestParams.addProperty("name", "Ronak Jain");
		requestParams.addProperty("emailId", "rnak1891@gmail.com");
		requestParams.addProperty("branch", "Computer Science");
		requestParams.addProperty("age", 32);
		
        Response response = request.body(requestParams.toString()).post();
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.get("name"), "Ronak Jain");
        Assert.assertEquals(jsonPath.get("branch"), "Computer Science");
        }	
	
	@Test
	public void validateErrorIfEmailExists() {
		
		JsonObject requestParams = new JsonObject();
		requestParams.addProperty("name", "Ronak Jain");
		requestParams.addProperty("emailId", "rnak1891@gmail.com");
		requestParams.addProperty("branch", "Computer Science");
		requestParams.addProperty("age", 32);
		
        Response response = request.body(requestParams.toString()).post();
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.get("error"), "Email ID already exists");
	}
}
