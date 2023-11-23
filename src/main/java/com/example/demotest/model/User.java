package com.example.demotest.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @Column(name = "name")
    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    @Column(name = "phone")
    private String phone;

    @NotBlank
    @Column(name = "password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}