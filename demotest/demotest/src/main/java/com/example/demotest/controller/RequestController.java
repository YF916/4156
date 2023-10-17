package com.example.demotest.controller;

import com.example.demotest.model.Request;
import com.example.demotest.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping(path="/request")
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;

    @PostMapping(path="/submit")
    public @ResponseBody String submitRequest(@RequestBody Request request) {
        request.setRequestTime(LocalDateTime.now());
        request.setStatus("PENDING");
        requestRepository.save(request);
        return "Request Submitted";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Request> getAllRequests() {
        return requestRepository.findAll();
    }
}
