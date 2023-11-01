package org.example.microservice_scooter.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Getter
@RequiredArgsConstructor
public class PauseDTO {
    private long id;
    private Timestamp startTime;
    private Timestamp endTime;
    private int time;


}

