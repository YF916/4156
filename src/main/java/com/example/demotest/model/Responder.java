package com.example.demotest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.annotations.Latitude;
import org.hibernate.search.annotations.Longitude;
import org.hibernate.search.annotations.Spatial;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Spatial
@Table(name = "Responder")
public class Responder {
    @Id
    @Column(name = "name")
    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    @Column(name = "phone")
    private String phone;

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

    @Column(name = "status")
    private String status;

    @Column(name = "rating")
    private Double rating;
    @JsonIgnore
    @Column(name = "password")
    private String password;
    public Responder() {}
    public Responder(Responder original) {
        this.name = original.getName();
        this.phone = original.getPhone();
        this.latitude = original.getLatitude();
        this.longitude = original.getLongitude();
        this.status = original.getStatus();
        this.rating = original.getRating();
        this.password = original.getPassword();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
