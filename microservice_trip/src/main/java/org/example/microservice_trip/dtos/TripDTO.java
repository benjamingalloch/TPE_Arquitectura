package org.example.microservice_trip.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.microservice_trip.entities.Trip;

import java.sql.Timestamp;

@Getter
@RequiredArgsConstructor
public class TripDTO {

    private long userId;
    private long scooterId;
    // private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp useTime;
    private double pauseTime;
    private double kilometers;
    //private Double pause;
    private Double rate;

    public TripDTO(Trip trip) {
        this.userId = trip.getUserId();
        this.scooterId = trip.getScooterId();
        this.useTime = trip.getUseTime();
        this.pauseTime = trip.getPauseTime();
        this.rate = trip.getRate();
        this.endTime = trip.getEndTime();
        this.kilometers = trip.getKilometers();
    }

    public TripDTO(long userId, long scooterId, Timestamp useTime, double pauseTime, Double rate, Timestamp endTime, double kilometers) {
        this.userId = userId;
        this.scooterId = scooterId;
        this.useTime = useTime;
        this.pauseTime = pauseTime;
        this.rate = rate;
        this.endTime = endTime;
        this.kilometers = kilometers;
    }
}

