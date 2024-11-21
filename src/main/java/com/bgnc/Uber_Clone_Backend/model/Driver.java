package com.bgnc.Uber_Clone_Backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("DRIVER")
public class Driver extends User {

    private String licenseNumber;
    private String vehiclePlate;
    private String vehicleModel;
    private Integer vehicleYear;

    @Column(columnDefinition = "decimal(3,2)")
    private Double rating;

    private Boolean isAvailable;

    @Embedded
    private Location currentLocation;

    @PostPersist
    private void initializeDriver() {
        this.rating = 0.0;
        this.isAvailable = false;
    }
}