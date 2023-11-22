package com.example.demotest.controller;


import com.example.demotest.exceptions.InvalidRequestException;
import com.example.demotest.exceptions.UserAlreadyExistException;
import com.example.demotest.model.User;
import com.example.demotest.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/authenticate/user")
    public String authenticateUser(@RequestBody Map<String, String> requestBody) {
        if (!requestBody.containsKey("username") || !requestBody.containsKey("password")){
            throw new InvalidRequestException("request body should have username and password");
        }
        return authenticationService.authenticateUser(requestBody.get("username"), requestBody.get("password"), "user");
    }
    @PostMapping("/authenticate/responder")
    public String authenticateResponder(@RequestBody Map<String, String> requestBody) {
        if (!requestBody.containsKey("username") || !requestBody.containsKey("password")){
            throw new InvalidRequestException("request body should have username and password");
        }
        return authenticationService.authenticateUser(requestBody.get("username"), requestBody.get("password"), "responder");
    }
}