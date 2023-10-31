package org.example.microservice_scooter.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import org.example.microservice_scooter.dtos.ScooterDTO;

@Entity
@Data
@Table(name = "scooter")
public class Scooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "status")
    private String status;

    @Column(name = "kilometers")
    private double kilometers;

    @Column(name = "use_time")
    private int useTime;

    public Scooter() {
        super();
        this.status = "Free";
        this.kilometers = 0;
        this.useTime = 0;
        this.latitude = 0;
        this.longitude = 0;
    }

    public Scooter(@NonNull ScooterDTO dto) {
        super();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.status = dto.getStatus();
        this.kilometers = dto.getKilometers();
        this.useTime = dto.getUseTime();
    }

    public void updateFromDTO(@NonNull ScooterDTO scooter){
        this.latitude = scooter.getLatitude();
        this.longitude = scooter.getLongitude();
        this.status = scooter.getStatus();
        this.kilometers = scooter.getKilometers();
        this.useTime = scooter.getUseTime();
    }
}
