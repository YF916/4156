package com.example.demotest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demotest.model.Responder;

import java.util.List;

public interface ResponderRepository extends JpaRepository<Responder, Integer> {
    List<Responder> findAllByRatingGreaterThanAndStatusEqualsOrderByRatingDesc(double rating, String status);
    List<Responder> findAllByStatusEquals(String status);

    Iterable<Responder> findByLatitudeBetweenAndLongitudeBetween(Double minLat, Double maxLat, Double minLon, Double maxLon);
}
