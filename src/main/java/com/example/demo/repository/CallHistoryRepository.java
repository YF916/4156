package com.example.demo.repository;

import com.example.demo.model.CallHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallHistoryRepository extends CrudRepository<CallHistory, Integer> {
}
