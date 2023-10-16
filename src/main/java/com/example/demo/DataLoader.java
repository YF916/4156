package com.example.demo;

import com.example.demo.model.CallHistory;
import com.example.demo.model.Client;
import com.example.demo.repository.CallHistoryRepository;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private final ClientRepository clientRepository;
    private final CallHistoryRepository callHistoryRepository;

    @Autowired
    public DataLoader(ClientRepository clientRepository, CallHistoryRepository callHistoryRepository){
        this.clientRepository = clientRepository;
        this.callHistoryRepository = callHistoryRepository;
    }

    public void run(ApplicationArguments args) {
        clientRepository.save(new Client(1, "name1", 22, "address1"));
        clientRepository.save(new Client(2, "name2", 23, "address2"));
        clientRepository.save(new Client(3, "name3", 24, "address3"));
        clientRepository.save(new Client(4, "name4", 25, "address4"));
        clientRepository.save(new Client(5, "name5", 26, "address5"));
        clientRepository.save(new Client(6, "name6", 27, "address6"));

        callHistoryRepository.save(new CallHistory(1, 1, 2, 3));
        callHistoryRepository.save(new CallHistory(2, 1, 3, 1));
        callHistoryRepository.save(new CallHistory(3, 1, 4, 5));
        callHistoryRepository.save(new CallHistory(4, 1, 5, 4));
        callHistoryRepository.save(new CallHistory(5, 1, 5, 1));
        callHistoryRepository.save(new CallHistory(6, 1, 5, 2));
        callHistoryRepository.save(new CallHistory(7, 1, 5, 3));
        callHistoryRepository.save(new CallHistory(8, 2, 8, 3));
        callHistoryRepository.save(new CallHistory(9, 2, 8, 1));
        callHistoryRepository.save(new CallHistory(10, 2, 8, 5));
        callHistoryRepository.save(new CallHistory(11, 2, 9, 4));
        callHistoryRepository.save(new CallHistory(12, 2, 9, 1));
        callHistoryRepository.save(new CallHistory(13, 2, 9, 2));
        callHistoryRepository.save(new CallHistory(14, 2, 7, 3));

    }
}
