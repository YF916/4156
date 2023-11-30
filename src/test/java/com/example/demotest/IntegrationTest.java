package com.example.demotest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class IntegrationTest {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void testUserRegister() {
        String requestBody = "{\"name\":\"testUser\", \"phone\":\"0000000000\", \"password\":\"testPass\"}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/register/user")
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case 200:
                break;
            case 409:
                assertEquals("Username already exists", response.getBody().asString());
                break;
            default:
                fail("Unexpected status code: " + statusCode);
        }
    }

    @Test
    public void testUserLogin() {
        String requestBody = "{\"username\":\"testUser\", \"password\":\"testPass\"}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/authenticate/user")
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case 200:
                break;
            default:
                fail("Unexpected status code: " + statusCode);
        }

        String token = response.getBody().asString();
        // System.out.println(token);

        testUserSearch(token);
        testUserRequest(token);
        testUserRate(token, 9, 8);
        testUserAll(token);

    }

    private void testUserSearch(String token) {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get("/dispatch-history/search?status=pending")
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case 200:
                break;
            default:
                fail("Unexpected status code: " + statusCode);
        }

    }

    private void testUserRequest(String token) {
        String dispatchJson = "{\"emergencyLevel\":2, \"emergencyType\": \"medical\", \"latitude\": 10.10, \"longitude\": 20.20}";

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(dispatchJson)
                .when()
                .post("/user/send_request")
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case 200:
                break;
            case 400:
                assertEquals("you already have an active request", response.getBody().asString());
                break;
            default:
                fail("Unexpected status code: " + statusCode);
        }
    }

    private void testUserRate(String token, int id, int rating) {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .param("id", id)
                .param("rating", rating)
                .when()
                .post("/dispatch-history/rate")
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case 200:
                break;
            case 400:
                assertEquals("this request has already been rated", response.getBody().asString());
                break;
            default:
                fail("Unexpected status code: " + statusCode);
        }
    }

    private void testUserAll(String token) {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get("/user/self")
                .then()
                .extract().response();

        // String result = response.getBody().asString();
        // System.out.println(result);

        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case 200:
                break;
            default:
                fail("Unexpected status code: " + statusCode);
        }

    }

    @Test
    public void testResponderRegister() {
        String requestBody = "{\"name\": \"testResponder\", \"phone\": \"1111111111\", \"longitude\": 10.10, \"latitude\": 20.20, \"password\": \"12341234\"}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/register/responder")
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case 200:
                break;
            case 409:
                assertEquals("Username already exists", response.getBody().asString());
                break;
            default:
                fail("Unexpected status code: " + statusCode);
        }
    }

    @Test
    public void testResponderLogin() {
        String requestBody = "{\"username\":\"testResponder\", \"password\":\"12341234\"}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/authenticate/responder")
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case 200:
                break;
            default:
                fail("Unexpected status code: " + statusCode);
        }

        String token = response.getBody().asString();
        // System.out.println(token);

        testResponderSearch(token);
        testResponderSearchWithinDistance(token);
        testResponderAccept(token, 9);

    }

    private void testResponderSearch(String token) {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get("/responder/search")
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case 200:
                break;
            default:
                fail("Unexpected status code: " + statusCode);
        }

    }

    private void testResponderSearchWithinDistance(String token) {
        Double longitude = 10.50;
        Double latitude = 20.50;
        Double radius = 5.00;
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .param("longitude", longitude)
                .param("latitude", latitude)
                .param("radius", radius)
                .contentType(ContentType.JSON)
                .when()
                .get("/responder/search_distance")
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case 200:
                break;
            default:
                fail("Unexpected status code: " + statusCode);
        }

    }

    private void testResponderAccept(String token, int id) {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .post("/responder/accept/" + id)
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case 200:
                break;
            case 409:
                assertEquals("Request is already handled", response.getBody().asString());
                break;
            default:
                fail("Unexpected status code: " + statusCode);
        }
    }

}
