package org.example.microservice_trip.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Getter
@RequiredArgsConstructor
public class ScooterDTO {
    private long id;
    private double latitude;
    private double longitude;
    private String status;
    private double kilometers;
    private int useTime;
    private List<PauseDTO> pauses;

    public ScooterDTO(ScooterDTO scooter) {
        this.id = scooter.getId();
        this.latitude = scooter.getLatitude();
        this.longitude = scooter.getLongitude();
        this.status = scooter.getStatus();
        this.kilometers = scooter.getKilometers();
        this.useTime = scooter.getUseTime();
        this.pauses.addAll(scooter.getPauses());
    }

    public ScooterDTO(double latitude, double longitude, String status, double kilometers, int useTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.kilometers = kilometers;
        this.useTime = useTime;
    }
}
