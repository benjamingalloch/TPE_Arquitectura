package org.example.microservice_trip.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ScooterDTO {
    private long id;
    private double latitude;
    private double longitude;
    private String status;
    private double kilometers;
    private int useTime;

    public ScooterDTO(double latitude, double longitude, String status, double kilometers, int useTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.kilometers = kilometers;
        this.useTime = useTime;
    }
}
