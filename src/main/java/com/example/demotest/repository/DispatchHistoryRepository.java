package com.example.demotest.repository;

import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import com.example.demotest.model.DispatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispatchHistoryRepository extends JpaRepository<DispatchHistory, Integer> {
    List<DispatchHistory> findByResponderAndStatus(Responder responder, String status);
    List<DispatchHistory> findAllByStatusOrderByEmergencyLevelAsc(String status);
    List<DispatchHistory> findByCallerAndStatus(User user, String status);
    List<DispatchHistory> findByCallerAndStatusNot(User user, String status);
    List<DispatchHistory> findByResponder(Responder responder);
    List<DispatchHistory> findByCaller(User user);
    boolean existsByIdAndStatusNotAndCaller(Integer id, String status,User caller);
    List<DispatchHistory> findAllByLatitudeBetweenAndLongitudeBetweenAndStatusEqualsOrderByEmergencyLevel(Double minLat, Double maxLat, Double minLon, Double maxLon,String status);
}
