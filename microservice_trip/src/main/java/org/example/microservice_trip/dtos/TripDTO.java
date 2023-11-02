package org.example.microservice_trip.dtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.microservice_trip.entities.Trip;

import java.sql.Timestamp;

@Getter
@RequiredArgsConstructor
public class TripDTO {

    private long id;
    private long userId;
    private long scooterId;
    private Timestamp startTime;
    private Timestamp endTime;
    private int pauseTime;
    private double kilometers;
    private double price;

    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.userId = trip.getUserId();
        this.scooterId = trip.getScooterId();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.pauseTime = trip.getPauseTime();
        this.kilometers = trip.getKilometers();
        this.price = trip.getPrice();
    }

    public TripDTO(long userId, long scooterId, Timestamp startTime, Timestamp endTime,
                   int pauseTime, double kilometers, double price) {
        this.userId = userId;
        this.scooterId = scooterId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pauseTime = pauseTime;
        this.kilometers = kilometers;
        this.price = price;
    }
}

