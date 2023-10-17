package com.example.demotest.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "dispatch_history_id")
    private DispatchHistory dispatchHistory;

    @Column(name = "submission_time")
    private LocalDateTime submissionTime;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private int rating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DispatchHistory getDispatchHistory() {
        return dispatchHistory;
    }

    public void setDispatchHistory(DispatchHistory dispatchHistory) {
        this.dispatchHistory = dispatchHistory;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

