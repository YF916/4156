package com.example.demotest.repository;

import com.example.demotest.model.Feedback;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {
}
