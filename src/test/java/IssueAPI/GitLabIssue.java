package IssueAPI;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;

import com.jayway.jsonpath.JsonPath;

//import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;


public class GitLabIssue {
	RequestSpecification requestspecification;
	ValidatableResponse validatableResponse;
	String validateResponse;
	Response response;
	String accessToken = "glpat-S3B3EKJx1iKxZYtr5kEz";
	
	@BeforeClass
	public void init() {
		requestspecification = given().
		baseUri("https://gitlab.com/api/v4/")
		.header("Authorization", "Bearer " + accessToken);
		
	}
	
	@org.testng.annotations.Test
	 public void listProjects() {
		validatableResponse = given().spec(requestspecification).
				pathParam("id","35479690").
				
		when().
				get("/projects/{id}").
		then().assertThat().
		statusCode(200);
			
			
	}
	@org.testng.annotations.Test
	 public void listAllIssues() {
		validateResponse = given().spec(requestspecification).
				pathParam("id","35483922").
				
		when().
				get("/projects/{id}/issues").asString();
		String completeResponse = JsonPath.
				read(validateResponse, "$").toString();
		System.out.println(completeResponse);
				
	}
	@org.testng.annotations.Test
	 public void validateProjectswithQueryParam() {
		validatableResponse = given().spec(requestspecification).
				pathParam("id","35479690").
				queryParam("simple","true").
				queryParam("id_after","35482769").
		when().
				get("/projects/{id}").
		then().
			assertThat().
			statusCode(200);	
	}
	
	
	@org.testng.annotations.Test
	 public void validateIssuesIsEmpty() {
		validatableResponse = given().spec(requestspecification).
	
				pathParam("id","35479690").				
		when().
				get("/projects/{id}/issues").
		then().log().all().body("", Matchers.hasSize(0)).
			assertThat().
			statusCode(200);	
	}

	
	
	@org.testng.annotations.Test
	 public void createNewIssue() {
		validatableResponse = given().spec(requestspecification).
			pathParam("id","35483922").
			queryParam("title","sud").
			queryParam("state_event","opened").
					
		when().
		post("/projects/{id}/issues").
				
		then().log().all().assertThat().statusCode(201);
		
		
	}
	
	@org.testng.annotations.Test
    public void delete_issue() {
		validatableResponse = given().spec(requestspecification).
			pathParam("id","35483922").
			pathParam("issue_iid","1").
	when().
			delete("/projects/{id}/issues/{issue_iid}").
	then().
		assertThat().
		statusCode(204);	
}
	
	@org.testng.annotations.Test
  public void Edit_issue() {
		validateResponse = given().spec(requestspecification).
			pathParam("id","35483922").
			pathParam("issue_iid","4").
			queryParam("labels","bugs").
			queryParam("title","Issues with authentication").
			
	when().
			put("/projects/{id}/issues/{issue_iid}").asString();
		String completeResponse = JsonPath.
				read(validateResponse, "$.title").toString();
		System.out.println(completeResponse);
	
}
	
	@org.testng.annotations.Test
	 public void timeEstimateForissue() {
		String validateResponse = given().spec(requestspecification).
				pathParam("id","35483922").
				pathParam("issue_iid","5").
				queryParam("duration","30m").
		when().
				post("/projects/{id}/issues/{issue_iid}/time_estimate").asString();
		String completeResponse = JsonPath.
				read(validateResponse, "$.human_time_estimate").toString();
		System.out.println(completeResponse);
		
		
					
	}
}

