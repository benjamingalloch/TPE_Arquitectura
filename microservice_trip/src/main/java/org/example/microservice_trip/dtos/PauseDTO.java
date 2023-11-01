package org.example.microservice_trip.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Getter
@RequiredArgsConstructor
public class PauseDTO {
    private long userId;
    private long tripId;
    private long scooterId;
    private Timestamp startTime;
    private Timestamp endTime;
    private int time;

    public PauseDTO(PauseDTO pause){
        this.userId = pause.getUserId();
        this.tripId = pause.getTripId();
        this.scooterId = pause.getScooterId();
        this.startTime = pause.getStartTime();
        this.endTime = pause.getEndTime();
        this.time = pause.getTime();
    }
}

