package org.example.microservice_trip.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "rate")

public class Rate {
    @Column(name = "rateId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rateId;
    @Column(name = "date")
    private Timestamp date;
    @Column(name = "flatRate")
    private double flatRate;
    @Column(name = "fullRate")
    private double fullRate;

    public Rate() {
        super();
    }

    public Rate(double flatRate, double fullRate) {
        this(flatRate, fullRate,  new Timestamp(System.currentTimeMillis()));
    }

    public Rate(double flatRate, double fullRate, Timestamp date) {
        this.flatRate = flatRate;
        this.fullRate = fullRate;
        this.date = date;
    }
}