package org.example.microservice_admin.DTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScooterUseTimeReportDTO {
    private long scooterId;
    private int useTime;
    private int pauseTime;
    private String status;


    public ScooterUseTimeReportDTO(String status, int useTime, int pauseTime) {
        this.status = status;
        this.useTime = useTime;
        this.pauseTime = pauseTime;
    }

}
