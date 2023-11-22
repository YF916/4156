package com.example.demotest.service;

import com.example.demotest.exceptions.NoSuchAccountException;
import com.example.demotest.model.User;
import com.example.demotest.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    public String authenticateUser(String username, String password, String role) throws NoSuchAccountException {
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println(auth);
        } catch (AuthenticationException exception) {
            System.out.println("AuthenticationException 1");
            throw new NoSuchAccountException("Account credentials does not match ");

        }
        if (auth == null || !auth.isAuthenticated() || !auth.getAuthorities().contains(new SimpleGrantedAuthority(role))) {
            throw new NoSuchAccountException("Account credentials does not match ");
        }
        return jwtUtil.generateToken(username, password, role);
    }
}
