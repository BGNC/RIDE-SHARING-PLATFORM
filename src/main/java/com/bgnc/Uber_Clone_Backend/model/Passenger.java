package com.bgnc.Uber_Clone_Backend.model;

import com.bgnc.Uber_Clone_Backend.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import javax.xml.stream.Location;

@Entity
@DiscriminatorValue("PASSENGER")
public class Passenger extends User{

    @Column(columnDefinition = "decimal(3,2)")
    private Double rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_payment_method")
    private PaymentMethod preferredPaymentMethod;

    @Embedded
    private Location homeAddress;



    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "work_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "work_longitude")),
            @AttributeOverride(name = "address", column = @Column(name = "work_address"))
    })
    private Location workAddress;


}
