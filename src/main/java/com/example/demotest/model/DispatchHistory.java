package com.example.demotest.model;

import org.hibernate.search.annotations.Latitude;
import org.hibernate.search.annotations.Longitude;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
@Entity
@Table(name = "DispatchHistory")
public class DispatchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User caller;
    @ManyToOne
    @JoinColumn(name = "responder_name")
    private Responder responder;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "arrival_time", nullable = true)
    private LocalDateTime arrivalTime;

    @Column(name = "rating", nullable = true)
    private Double rating;

    @Column(name = "feedback", nullable = true)
    private String feedback;

    @Column(name = "status")
    private String status;

    @NotNull
    @DecimalMin(value = "1", inclusive = true, message = "invalid emergency level")
    @DecimalMax(value = "5", inclusive = true, message = "invalid emergency level")
    @Column(name = "emergency_level")
    private int emergencyLevel;

    @NotBlank
    @Column(name = "emergency_type")
    private String emergencyType;

    @NotNull
    @Longitude
    @DecimalMin(value = "-90.0", inclusive = true, message = "invalid latitude")
    @DecimalMax(value = "90.0", inclusive = true, message = "invalid latitude")
    @Column(name = "latitude")
    private Double latitude;

    @NotNull
    @Latitude
    @DecimalMin(value = "-180.0", inclusive = true, message = "invalid latitude")
    @DecimalMax(value = "180.0", inclusive = true, message = "invalid latitude")
    @Column(name = "longitude")
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name = "message")
    private String message;

    public int getEmergencyLevel() {return emergencyLevel;}

    public void setEmergencyLevel(int emergencyLevel) {this.emergencyLevel = emergencyLevel;}

    public String getEmergencyType() {return emergencyType;}

    public void setEmergencyType(String emergencyType) {this.emergencyType = emergencyType;}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Responder getResponder() {
        return responder;
    }

    public void setResponder(Responder responder) {
        this.responder = responder;
    }

    public User getCaller() {
        return caller;
    }

    public void setCaller(User caller) {
        this.caller = caller;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
