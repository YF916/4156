package com.example.demotest.service;

import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final ResponderRepository responderRepository;
    //private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository,ResponderRepository responderRepository) {
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
        this.responderRepository = responderRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userResult = userRepository.findById(username);
        Optional<Responder> responderResult = responderRepository.findById(username);
        if (userResult.isEmpty() && responderResult.isEmpty()) {
            throw new UsernameNotFoundException("Account not found with username: " + username);
        }else if (userResult.isPresent()){
            User user = userResult.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getName())
                    .password(user.getPassword())
                    .authorities("user").build();
        }else{
            Responder responder = responderResult.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(responder .getName())
                    .password(responder .getPassword())
                    .authorities("responder").build();
        }

    }
}
