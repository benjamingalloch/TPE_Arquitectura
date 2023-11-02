package org.example.microservice_trip.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.microservice_trip.dtos.TripDTO;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "scooter_id")
    private long scooterId;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "kilometers")
    private double kilometers;

    @Column(name = "pause_time")
    private int pauseTime;

    @Column(name = "price")
    private Double price;

    public Trip(){
        super();
    }

    public Trip(long userId, long scooterId, Timestamp startTime, Timestamp endTime, int pauseTime, Double price, double kilometers) {
        this.userId = userId;
        this.scooterId = scooterId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.kilometers = kilometers;
        this.pauseTime = pauseTime;
        this.price = price;
    }

    public void updateFromDTO(TripDTO tripDTO) {
        this.userId = tripDTO.getUserId();
        this.scooterId = tripDTO.getScooterId();
        this.startTime = tripDTO.getStartTime();
        this.endTime = tripDTO.getEndTime();
        this.kilometers = tripDTO.getKilometers();
        this.pauseTime = tripDTO.getPauseTime();
        this.price = tripDTO.getPrice();
    }
}