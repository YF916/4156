package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/client", produces = "application/hal+json")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Client getClient(@PathVariable("id") final Integer id){
        return clientService.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Client> getAllClient(){
        return clientService.findAll();
    }

//    @RequestMapping(value = "/addClient", method = RequestMethod.POST)
//    public Client saveClient1(@RequestBody Client client){
//        return clientService.save(client);
//    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Client saveClient2(String name, Integer age, String adress){
        Client client = new Client();
        client.name = name;
        client.age = age;
        client.adress = adress;
        return clientService.save(client);
    }
}

// ./gradlew clean build
