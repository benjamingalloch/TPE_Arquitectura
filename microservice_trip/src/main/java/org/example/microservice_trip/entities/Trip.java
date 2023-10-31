package org.example.microservice_trip.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "travel")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name = "user_id")
    private long userId;
    @Column(name = "scooter_id")
    private long scooterId;
    @Column(name = "use_time")
    private Timestamp useTime;
    @Column(name = "end_time")
    private Timestamp endTime;
    @Column(name = "kilometers")
    private double kilometers;
    // @Column(name = "scooter_end_kms")
    // private double scooterEndKms;
    @Column(name = "pause_time")
    private int pauseTime;
    @Column(name = "rate")
    private Double rate;

    public Trip(){
        super();
    }

    // public Travel(long userId, long scooterId, Double rate, double scooterStartKms) {
    // 	this.userId = userId;
    // 	this.scooterId = scooterId;
    // 	this.startTime = new Timestamp(System.currentTimeMillis());
    // 	this.endTime = null;
    // 	this.scooterStartKms = scooterStartKms;
    // 	this.scooterEndKms = 0;
    // 	this.pause = Duration.ZERO;
    // 	this.rate = rate;
    // }

    public Trip(long userId, long scooterId, int pauseTime, Double rate, Timestamp useTime, double scooterInitKms) {
        this.userId = userId;
        this.scooterId = scooterId;
        this.useTime = useTime;
        this.kilometers = scooterInitKms;
        this.endTime = null;
        this.pauseTime = 0;
        this.rate = rate;
    }
}