package org.example.microservice_scooter.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.example.microservice_scooter.dtos.ScooterDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Table(name = "scooter")
public class Scooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @OneToMany(mappedBy = "scooter")
    private List<Pause> pauses;

    public Scooter() {
        super();
        this.status = "FREE";
        this.kilometers = 0;
        this.useTime = 0;
        this.latitude = 0;
        this.longitude = 0;
        this.pauses = new ArrayList<>();
    }

    public Scooter(@NonNull ScooterDTO dto) {
        super();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.status = dto.getStatus();
        this.kilometers = dto.getKilometers();
        this.useTime = dto.getUseTime();
        this.pauses = new ArrayList<>();
    }

    public void updateFromDTO(@NonNull ScooterDTO scooter){
        this.latitude = scooter.getLatitude();
        this.longitude = scooter.getLongitude();
        this.status = scooter.getStatus();
        this.kilometers = scooter.getKilometers();
        this.useTime = scooter.getUseTime();

    }

    public void addPause(Pause pause) {
        this.pauses.add(pause);
    }

    public Pause getPause(long userId, long tripId) { //SE PUEDE MEJORAR
        for (Pause pause : pauses) {
            if (pause.getScooter().equals(this)
                    && pause.getId().getUserId() == userId
                    && pause.getId().getTripId() == tripId) {
                return pause;
            }
        }
        return null; // Retorna null si no se encuentra ninguna pausa
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scooter scooter = (Scooter) o;

        return id == scooter.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}
