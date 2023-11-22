package com.example.demotest.controller;

import com.example.demotest.model.DispatchHistory;
import com.example.demotest.model.Responder;
import com.example.demotest.repository.DispatchHistoryRepository;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDateTime;

@Controller // This means that this class is a Controller
// This means URL's start with /dispatch-history (after Application path)
@RequestMapping(path = "/dispatch-history")
public class DispatchHistoryController {
    @Autowired
    private DispatchHistoryRepository dispatchHistoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResponderRepository responderRepository;

    @PostMapping(path = "/start") // called when the dispatch starts
    public @ResponseBody String addNewDispatchHistory(
            @RequestParam("user_name") String username,
            @RequestParam("responder_name") String responderName,
            @RequestParam("start_time")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime) {
        DispatchHistory history = new DispatchHistory();
        history.setCaller(userRepository.getReferenceById(username));
        history.setStartTime(startTime);
        history.setResponder(responderRepository.getReferenceById(responderName));
        dispatchHistoryRepository.save(history);
        return "Saved";
    }
    // when user want to give a feedback
    @PostMapping(path = "/rate")
    public @ResponseBody String rateDispatchHistory(@RequestParam int id,
                                                    @RequestParam Double rating,
                                                    @RequestParam(required = false) String feedback) {
        DispatchHistory dispatchToRate = dispatchHistoryRepository.getReferenceById(id);
        dispatchToRate.setRating(rating);
        if (feedback != null) {
            dispatchToRate.setFeedback(feedback);
        }
        Responder responder = responderRepository.getReferenceById(dispatchToRate.getResponder().getName());
        // rolling average
        responder.setRating(responder.getRating() * 0.9 + rating * 0.1);
        dispatchHistoryRepository.save(dispatchToRate);
        return "Rated";
    }

    @PostMapping(path = "/arrived") // called when dispatcher arrived on scene
    public @ResponseBody String updateArrivalTime(@RequestParam int id, @RequestParam("arrival_time")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalTime) {
        DispatchHistory dispatchToUpdate = dispatchHistoryRepository.getReferenceById(id);
        dispatchToUpdate.setArrivalTime(arrivalTime);
        dispatchHistoryRepository.save(dispatchToUpdate);
        return "Arrived";
    }

    // called when dispatcher arrived at scene
    @PostMapping(path = "/finished")
    public @ResponseBody String finishDispatch(@RequestParam int id) {
        DispatchHistory dispatchToUpdate = dispatchHistoryRepository.getReferenceById(id);
        dispatchToUpdate.setStatus("finished");
        dispatchHistoryRepository.save(dispatchToUpdate);
        return "Finished";
    }

    // search dispatch history by user id/ responder id or return all dispatch history
    @GetMapping(path = "/search")
    public @ResponseBody
    Iterable<DispatchHistory> searchDispatchHistory(@RequestParam(required = false) String filterBy,
                                                 @RequestParam(required = false) String name) {
        if ("Responder".equals(filterBy) && name != null) {
            return dispatchHistoryRepository.findByResponder(responderRepository.getReferenceById(name));
        } else if ("User".equals(filterBy) && name != null) {
            return dispatchHistoryRepository.findByCaller(userRepository.getReferenceById(name));
        } else {
            return dispatchHistoryRepository.findAll();
        }
    }

}
