package com.example.demotest.controller;

import com.example.demotest.exceptions.ResponderNotAvailableException;
import com.example.demotest.model.DispatchHistory;
import com.example.demotest.model.Responder;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.DispatchHistoryRepository;
import com.example.demotest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewResponder(Responder responder) {
        responder.setRating(10.0); //give the responder an initial rating of 10
        responder.setStatus("available");
        responderRepository.save(responder);
        return "Saved";
    }

    @RequestMapping(path = "/search")//search for all available responders, optionally sort by rating
    public @ResponseBody Iterable<Responder> getAllResponders(@RequestParam(required = false, defaultValue = "0.0") Double rating) {
        return responderRepository.findAllByRatingGreaterThanAndStatusEqualsOrderByRatingDesc(rating, "available");
    }

    @PostMapping(path = "/dispatch") // dispatch a specific responder by id
    public @ResponseBody String dispatchResponder(@RequestParam Integer id, @RequestParam String status,
                                                   @RequestParam Double latitude, @RequestParam Double longitude) {
        if (!responderRepository.existsById(id)) {
            throw new ResponderNotAvailableException("Responder does not exist");
        }
        Responder responderToDispatch = responderRepository.getReferenceById(id);
        if (!responderToDispatch.getStatus().equals("available")) {
            throw new ResponderNotAvailableException("Responder not available at this moment");
        }
        responderToDispatch.setStatus(status);
        responderToDispatch.setLongitude(longitude);
        responderToDispatch.setLatitude(latitude);
        responderRepository.save(responderToDispatch);
        return "Dispatched";
    }

    @GetMapping(path = "/recommend/rate") // called when the dispatch starts
    public @ResponseBody
    Responder getRateRecommend(@RequestParam("user_id") Integer id) {
        Iterable<DispatchHistory> allHistory = dispatchHistoryRepository.findByCaller(userRepository.getReferenceById(id));
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
    Responder getFreqRecommend(@RequestParam("user_id") Integer id) {
        Iterable<DispatchHistory> allHistory = dispatchHistoryRepository.findByCaller(userRepository.getReferenceById(id));
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
