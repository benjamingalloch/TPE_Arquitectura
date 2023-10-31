package org.example.microservice_scooter.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.microservice_scooter.entities.Scooter;

@Getter
@RequiredArgsConstructor
public class ScooterUseTimeReportDTO {
    private long scooterId;
    private int useTime;
    private String status;

    public ScooterUseTimeReportDTO(Scooter scooter) {
        this.scooterId = scooter.getId();
        this.status = scooter.getStatus();
        this.useTime = scooter.getUseTime();
    }

    public ScooterUseTimeReportDTO(String status, int useTime) {
        this.status = status;
        this.useTime = useTime;
    }

}
