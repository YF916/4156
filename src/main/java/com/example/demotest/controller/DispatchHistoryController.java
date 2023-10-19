package com.example.demotest.controller;

import com.example.demotest.model.DispatchHistory;
import com.example.demotest.model.User;
import com.example.demotest.model.Responder;
import com.example.demotest.repository.DispatchHistoryRepository;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/dispatch-history") // This means URL's start with /demo (after Application path)
public class DispatchHistoryController {
    @Autowired
    private DispatchHistoryRepository dispatchHistoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResponderRepository responderRepository;

    @PostMapping(path = "/start") // called when the dispatch starts
    public @ResponseBody String addNewDispatchHistory(@RequestParam("user_id") int userId,
                                                       @RequestParam("responder_id") int responderId,
                                                       @RequestParam("start_time")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime) {
        User user = userRepository.findById(userId).orElse(null);
        Responder responder = responderRepository.findById(responderId).orElse(null);

        if (user == null || responder == null) {
            throw new RuntimeException("User or Responder not found");
        }

        DispatchHistory history = new DispatchHistory();
        history.setCaller(user);
        history.setResponder(responder);
        history.setStartTime(startTime);
        dispatchHistoryRepository.save(history);

        return "Saved";
    }
    @PostMapping(path = "/rate") // when user want to give a feedback
    public @ResponseBody String rateDispatchHistory(@RequestParam int id,
                                                       @RequestParam int rating, @RequestParam(required = false) String feedback) {
        DispatchHistory dispatchToRate = dispatchHistoryRepository.getReferenceById(id);
        dispatchToRate.setRating(rating);
        if (feedback != null) {
            dispatchToRate.setFeedback(feedback);
        }
        //rate responder
        //if id does not exist
        dispatchHistoryRepository.save(dispatchToRate);
        return "Rated";
    }
    @PostMapping(path = "/arrived") // called when dispatcher arrived on scene
    public @ResponseBody String updateArrivalTime(@RequestParam int id, @RequestParam("arrival_time")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalTime) {
        DispatchHistory dispatchToUpdate = dispatchHistoryRepository.getReferenceById(id);
        dispatchToUpdate.setArrivalTime(arrivalTime);
        dispatchHistoryRepository.save(dispatchToUpdate);
        return "Rated";
    }
    @GetMapping(path = "/search") // search dispatch history by user id/ responder id or return all dispatch history
    public @ResponseBody
    Iterable<DispatchHistory> searchDispatchHistory(@RequestParam(required = false) String filterBy,
                                                 @RequestParam(required = false) Integer id) {
        if ("Responder".equals(filterBy) && id != null) {
            return dispatchHistoryRepository.findByResponder(responderRepository.getReferenceById(id));
        } else if ("User".equals(filterBy) && id != null) {
            return dispatchHistoryRepository.findByCaller(userRepository.getReferenceById(id));
        } else {
            return dispatchHistoryRepository.findAll();
        }
    }
}
