package org.example.microservice_admin.DTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScooterKilometersReportDTO {
    private long scooterId;
    private String status;
    private double kilometers;

    public ScooterKilometersReportDTO(ScooterDTO scooter) {
        this.scooterId = scooter.getId();
        this.status = scooter.getStatus();
        this.kilometers = scooter.getKilometers();
    }

    public ScooterKilometersReportDTO(String status, double kilometers) {
        this.status = status;
        this.kilometers = kilometers;
    }

}
