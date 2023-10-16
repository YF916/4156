package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue
    public Integer id;

    @Column(name = "name")
    public String name;

    @Column(name = "age")
    public Integer age;

    @Column(name = "adress")
    public String adress;
}
