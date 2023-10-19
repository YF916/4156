package com.example.demotest.model;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "DispatchHistory")
public class DispatchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User caller;
    @ManyToOne
    @JoinColumn(name = "responder_id")
    private Responder responder;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "arrival_time", nullable = true)
    private LocalDateTime arrivalTime;

    @Column(name = "rating", nullable = true)
    private int rating;
    @Column(name = "feedback", nullable = true)
    private String feedback;
    @Column(name = "status")
    private String status;


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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}
}
