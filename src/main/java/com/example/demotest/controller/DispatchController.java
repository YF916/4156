package com.example.demotest.controller;

import com.example.demotest.exceptions.RequestAlreadyHandledException;
import com.example.demotest.model.DispatchHistory;
import com.example.demotest.model.Responder;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.DispatchHistoryRepository;
import com.example.demotest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(path = "/responder")
public class DispatchController {
    @Autowired
    private ResponderRepository responderRepository;
    @Autowired
    private DispatchHistoryRepository dispatchHistoryRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/search")//search for all active requests, sorted by emergency level
    public @ResponseBody Iterable<DispatchHistory> getAllRequests() {
        return dispatchHistoryRepository.findAllByStatusOrderByEmergencyLevelAsc("pending");
    }

    @RequestMapping(path = "/search_distance")//search for all active requests within certain distance, order by emergency level
    public @ResponseBody Iterable<DispatchHistory> getRequestByRadius (@RequestParam Double latitude,
                                                                   @RequestParam Double longitude,
                                                                   @RequestParam Double radius) {
        Double minLat = latitude - radius;
        Double maxLat = latitude + radius;
        Double minLon = longitude - radius;
        Double maxLon = longitude + radius;

        return dispatchHistoryRepository.findAllByLatitudeBetweenAndLongitudeBetweenAndStatusEqualsOrderByEmergencyLevel(minLat, maxLat, minLon, maxLon,"pending");
    }

    @PostMapping(path = "/accept/{id}") // responder accepts a request
    public @ResponseBody String dispatchResponder(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id) {
        String username = userDetails.getUsername();
        Responder responderToDispatch = responderRepository.getReferenceById(username);
        DispatchHistory request = dispatchHistoryRepository.getReferenceById(id);
        if (!request.getStatus().equals("pending")) {
            throw new RequestAlreadyHandledException("Request is already handled");
        }
        request.setStatus("dispatched");
        request.setResponder(responderToDispatch);
        dispatchHistoryRepository.save(request);
        return "Accepted";
    }

    @GetMapping(path = "/recommend/rate") // called when the dispatch starts
    public @ResponseBody
    Responder getRateRecommend(@RequestParam("user_name") String name) {
        Iterable<DispatchHistory> allHistory = dispatchHistoryRepository.findByCaller(userRepository.getReferenceById(name));
        Responder responder = null;
        Double maxRate = -1.0;
        for (DispatchHistory history: allHistory) {
            if (history.getRating() != null) {
                if (history.getRating() > maxRate) {
                    maxRate = history.getRating();
                    responder = history.getResponder();
                }
            }
        }
        return responder;
    }

    @GetMapping(path = "/recommend/frequency") // called when the dispatch starts
    public @ResponseBody
    Responder getFreqRecommend(@RequestParam("user_name") String name) {
        Iterable<DispatchHistory> allHistory = dispatchHistoryRepository.findByCaller(userRepository.getReferenceById(name));
        ArrayList<Responder> responders = new ArrayList<>();
        for (DispatchHistory history: allHistory) {
            responders.add(history.getResponder());
        }
        Map<Responder, Integer> countMap = new HashMap<>();
        for (Responder r: responders) {
            Integer count = countMap.get(r);
            if (count == null) {
                count = 0;
            }
            count++;
            countMap.put(r, count);
        }

        Map.Entry<Responder, Integer> mostRepeated = null;
        for (Map.Entry<Responder, Integer> e: countMap.entrySet()) {
            if (mostRepeated == null || mostRepeated.getValue() < e.getValue()) {
                mostRepeated = e;
            }
        }
        try {
            return mostRepeated.getKey();
        } catch (NullPointerException e) {
            return null;
        }
    }

}
