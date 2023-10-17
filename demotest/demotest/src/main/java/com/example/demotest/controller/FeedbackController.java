package com.example.demotest.controller;

import com.example.demotest.model.DispatchHistory;
import com.example.demotest.model.Feedback;
import com.example.demotest.repository.DispatchHistoryRepository;
import com.example.demotest.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private DispatchHistoryRepository dispatchHistoryRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addFeedback(@RequestParam Integer dispatchHistoryId, @RequestParam String comment) {
        DispatchHistory dispatchHistory = dispatchHistoryRepository.findById(dispatchHistoryId).orElse(null);
        if (dispatchHistory == null) {
            return "Dispatch history not found!";
        }

        Feedback feedback = new Feedback();
        feedback.setDispatchHistory(dispatchHistory);
        feedback.setComment(comment);

        feedbackRepository.save(feedback);
        return "Feedback added!";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }
}

