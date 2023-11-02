package org.example.microservice_scooter.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.microservice_scooter.entities.Scooter;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ScooterDTO {
    private long id;
    private double latitude;
    private double longitude;
    private String status;
    private double kilometers;
    private int useTime;


    public ScooterDTO(Scooter scooter) {
        this.id = scooter.getId();
        this.latitude = scooter.getLatitude();
        this.longitude = scooter.getLongitude();
        this.status = scooter.getStatus();
        this.kilometers = scooter.getKilometers();
        this.useTime = scooter.getUseTime();
    }

    public ScooterDTO(double latitude,
                      double longitude,
                      String status,
                      double kilometers,
                      int useTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.kilometers = kilometers;
        this.useTime = useTime;
    }
}
