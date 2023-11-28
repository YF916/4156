package com.example.demotest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
                // Assert specific output for status code 409
                assertEquals("you already have an active request", response.getBody().asString());
                break;
            default:
                fail("Unexpected status code: " + statusCode);
        }

    }

}
