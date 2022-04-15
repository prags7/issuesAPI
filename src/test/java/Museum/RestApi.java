package Museum;


import org.hamcrest.Matcher;
import org.testng.annotations.*;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.*;
import static io.restassured.RestAssured.*;
import java.util.concurrent.TimeUnit;

import io.restassured.module.jsv.JsonSchemaValidator;
import static org.hamcrest.Matchers.lessThan;


public class RestApi {
	RequestSpecification requestspecification;
	Response response;
	
	
	@BeforeClass
	public void beforeClass() {
		requestspecification = given().
		baseUri("https://www.rijksmuseum.nl/api").
		param("key","0fiuZFh4");
		//response = requestspecification.get();
	}
	
	@org.testng.annotations.Test
	public void validate_status_code() {
		System.out.println("hi");
		given().spec(requestspecification).
				pathParam("culture","en").
		when().
				get("/{culture}/collection").
		then().
			assertThat().
			statusCode(200);
		
	}
	
	@org.testng.annotations.Test
public void error_status_code() {
	given().given().spec(requestspecification).
			param("key","0fiuZFh4").
			
			pathParam("culture","").
	when().
			get("/{culture}/collection").
	then().
		assertThat().
		statusCode(404);
	
}
	@org.testng.annotations.Test
	public void validate_Schema() {
		given().spec(requestspecification).
				pathParam("object-number","BK-15613").
				pathParam("culture","nl").
		when().
				get("/{culture}/collection/{object-number}").
		then().
		log().all().
		assertThat().
		body(JsonSchemaValidator.matchesJsonSchema("schema.json"));		
		
	}
	@org.testng.annotations.Test
	public void validateResposneTimeSuccess() {
		given().spec(requestspecification).
				pathParam("object-number","BK-15613").
				pathParam("culture","nl").
		when().
				get("/{culture}/collection/{object-number}/tiles").
		then().
		log().all().
		assertThat().time(lessThan(3000L));
	}
	@org.testng.annotations.Test
	public void validateResponseFields() {
		given().spec(requestspecification).
				pathParam("object-number","BK-15613").
				pathParam("culture","nl").
		when().
				get("/{culture}/collection/{object-number}").
		then().
		log().all();
	}
	
}