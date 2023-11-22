package com.example.demotest.service;


import com.example.demotest.exceptions.UserAlreadyExistException;
import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final ResponderRepository responderRepository;
    //private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository,ResponderRepository responderRepository) {
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
        this.responderRepository = responderRepository;
    }

    @Transactional
    public void addUser(User user) {
        if (userRepository.existsById(user.getName())|| responderRepository.existsById(user.getName())) {
            throw new UserAlreadyExistException ("Username already exists");
        }
        //passwordEncoder.encode(
        user.setPassword(user.getPassword());
        userRepository.save(user);
    }
    @Transactional
    public void addResponder(Responder responder) {
        if (userRepository.existsById(responder.getName())|| responderRepository.existsById(responder.getName())) {
            throw new UserAlreadyExistException("Username already exists");
        }
        //passwordEncoder.encode(
        responder.setPassword(responder.getPassword());
        responder.setRating(10.0); //give the responder an initial rating of 10
        responder.setStatus("available");
        responderRepository.save(responder);
    }
}
