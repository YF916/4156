package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "callhistory")
public class CallHistory {
    @Id
    @GeneratedValue
    public Integer id;

    @Column(name = "clientid")
    public Integer clientid;

    @Column(name = "responderid")
    public Integer responderid;

    @Column(name = "rating")
    public Integer rating;
}