package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import javax.persistence.EntityNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public Client findById(int id){
        return clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Iterable<Client> findAll(){
        return clientRepository.findAll();
    }

    public Client save(Client client){
        return clientRepository.save(client);
    }


}