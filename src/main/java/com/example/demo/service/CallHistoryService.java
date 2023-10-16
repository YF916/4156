package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.CallHistory;
import com.example.demo.repository.CallHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import javax.persistence.EntityNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CallHistoryService {

    private final CallHistoryRepository callHistoryRepository;

    @Autowired
    public CallHistoryService(CallHistoryRepository callHistoryRepository){
        this.callHistoryRepository = callHistoryRepository;
    }

    public CallHistory findById(int id){
        return callHistoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<CallHistory> findAll(){
        return callHistoryRepository.findAll();
    }

    public CallHistory save(CallHistory callHistory){
        return callHistoryRepository.save(callHistory);
    }


}

