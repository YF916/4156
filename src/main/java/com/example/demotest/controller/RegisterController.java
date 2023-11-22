package com.example.demotest.controller;


import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import com.example.demotest.service.AuthenticationService;
import com.example.demotest.service.RegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register/user")
    public void addUser(@RequestBody User user) {
        registerService.addUser(user);
    }

    @PostMapping("/register/responder")
    public void addResponder(@RequestBody Responder responder) {registerService.addResponder(responder);}
}