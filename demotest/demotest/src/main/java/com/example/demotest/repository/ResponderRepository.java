package com.example.demotest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demotest.model.Responder;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ResponderRepository extends JpaRepository<Responder, Integer> {

}
