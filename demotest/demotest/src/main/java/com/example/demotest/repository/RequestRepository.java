package com.example.demotest.repository;

import com.example.demotest.model.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, Integer> {
}
