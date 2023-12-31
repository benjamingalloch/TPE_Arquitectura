package org.example.microservice_scooter.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.microservice_scooter.entities.Pause;

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

    public PauseDTO(Pause pause){
        this.userId = pause.getId().getUserId();
        this.tripId = pause.getId().getTripId();
        this.scooterId = pause.getScooter().getId();
        this.startTime = pause.getStartTime();
        this.endTime = pause.getEndTime();
        this.time = pause.getTime();
    }
}

