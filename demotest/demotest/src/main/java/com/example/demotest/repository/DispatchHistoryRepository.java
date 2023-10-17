package com.example.demotest.repository;

import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import com.example.demotest.model.DispatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispatchHistoryRepository extends JpaRepository<DispatchHistory, Integer> {
    List<DispatchHistory> findByResponder(Responder responder);
    List<DispatchHistory> findByCaller(User user);
}
