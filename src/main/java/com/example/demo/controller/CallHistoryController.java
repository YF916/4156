package com.example.demo.controller;

import com.example.demo.model.CallHistory;
import com.example.demo.model.Client;
import com.example.demo.repository.CallHistoryRepository;
import com.example.demo.service.CallHistoryService;
import com.example.demo.service.ClientService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/history", produces = "application/hal+json")
public class CallHistoryController {

    private final ClientService clientService;
    private final CallHistoryService callHistoryService;

    @Autowired
    public CallHistoryController(ClientService clientService, CallHistoryService callHistoryService){
        this.clientService = clientService;
        this.callHistoryService = callHistoryService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CallHistory getHistory(@PathVariable("id") final Integer id){
        return callHistoryService.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<CallHistory> getAllHistory(){
        return callHistoryService.findAll();
    }

//    @RequestMapping(value = "/addClient", method = RequestMethod.POST)
//    public Client saveClient1(@RequestBody Client client){
//        return clientService.save(client);
//    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CallHistory saveCall(Integer clientid, Integer responderid){
        CallHistory callHistory = new CallHistory();
        callHistory.clientid = clientid;
        callHistory.responderid = responderid;
        return callHistoryService.save(callHistory);
    }

    @RequestMapping(value = "/recommend/rate/{id}", method = RequestMethod.GET)
    public Integer getRateRecommend(@PathVariable("id") final Integer clientid){
        Iterable<CallHistory> allHistory = callHistoryService.findAll();
        Integer responderid = 1;
        Integer maxRate = -1;
        for (CallHistory history: allHistory) {
            if (Objects.equals(history.clientid, clientid)) {
                if (history.rating > maxRate) {
                    maxRate = history.rating;
                    responderid = history.responderid;
                }
            }
        }
        return responderid;
    }

    @RequestMapping(value = "/recommend/frequency/{id}", method = RequestMethod.GET)
    public Integer getFreqRecommend(@PathVariable("id") final Integer clientid){
        Iterable<CallHistory> allHistory = callHistoryService.findAll();
        ArrayList<Integer> responders = new ArrayList<>();
        for (CallHistory history: allHistory) {
            if (Objects.equals(history.clientid, clientid)) {
                responders.add(history.responderid);
            }
        }

        Map<Integer, Integer> countMap = new HashMap<>();
        for(Integer i: responders)
        {
            Integer count = countMap.get(i);
            if(count == null) {
                count = 0;
            }
            count++;
            countMap.put(i, count);
            }

        Map.Entry<Integer, Integer> mostRepeated = null;
        for(Map.Entry<Integer, Integer> e: countMap.entrySet())
        {
            if(mostRepeated == null || mostRepeated.getValue() < e.getValue())
                mostRepeated = e;
        }
        try {
            return mostRepeated.getKey();
        } catch (NullPointerException e) {
            return -1;
        }
    }

}

