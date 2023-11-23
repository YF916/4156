package com.example.demotest.controller;


import com.example.demotest.exceptions.InvalidRequestException;
import com.example.demotest.model.DispatchHistory;
import com.example.demotest.model.User;
import com.example.demotest.repository.DispatchHistoryRepository;
import com.example.demotest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/user") // This means URL's start with /user
public class UserController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;
    @Autowired
    private DispatchHistoryRepository dispatchHistoryRepository;
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/send_request")
    public @ResponseBody String sendDispatchRequest(@AuthenticationPrincipal UserDetails userDetails, @RequestBody DispatchHistory history) {
        System.out.println("userDetails" + userDetails);

        String username = userDetails.getUsername();
        if (!dispatchHistoryRepository.findByCallerAndStatusNot(userRepository.findById(username).get(), "finished").isEmpty()){
            throw new InvalidRequestException("you already have an active request");
        }
        System.out.println(username);
        history.setCaller(userRepository.findById(username).get());
        history.setStatus("pending");
        history.setStartTime(LocalDateTime.now());
        dispatchHistoryRepository.save(history);
        return "request submitted";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}
