package com.example.demotest.controller;

import com.example.demotest.exceptions.InvalidRequestException;
import com.example.demotest.model.DispatchHistory;
import com.example.demotest.model.Responder;
import com.example.demotest.repository.DispatchHistoryRepository;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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

    // when user want to give a feedback
    @PostMapping(path = "/rate")
    public @ResponseBody String rateDispatchHistory(@AuthenticationPrincipal UserDetails userDetails, @RequestParam int id,
                                                    @RequestParam Double rating,
                                                    @RequestParam(required = false) String feedback) {
        if (!dispatchHistoryRepository.existsByIdAndStatusNotAndCaller(id,"pending",userRepository.getReferenceById(userDetails.getUsername()))){
            throw new InvalidRequestException("request with id " + id + " does not exist for this user or have not been accepted by a responder yet");
        }
        DispatchHistory dispatchToRate = dispatchHistoryRepository.getReferenceById(id);
        if (dispatchToRate.getRating()!=null){
            throw new InvalidRequestException("this request has already been rated");
        }
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
    public @ResponseBody String updateArrivalTime(@RequestParam int id) {
        LocalDateTime arrivalTime = LocalDateTime.now();
        DispatchHistory dispatchToUpdate = dispatchHistoryRepository.getReferenceById(id);
        dispatchToUpdate.setArrivalTime(arrivalTime);
        dispatchToUpdate.setStatus("arrived");
        dispatchHistoryRepository.save(dispatchToUpdate);
        return "Arrived";
    }

    // called when request is finished
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
    Iterable<DispatchHistory> searchDispatchHistory(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestParam(required = true) String status) {
        String username = userDetails.getUsername();
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("responder"))){
            return dispatchHistoryRepository.findByResponderAndStatus(responderRepository.getReferenceById(username),status);
        } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("user"))) {
            return dispatchHistoryRepository.findByCallerAndStatus(userRepository.getReferenceById(username),status);
        } else {
            throw new AccessDeniedException("Access is denied");
        }
    }
}
