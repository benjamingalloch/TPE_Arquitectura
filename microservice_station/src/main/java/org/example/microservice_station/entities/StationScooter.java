package org.example.microservice_station.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "station_scooter")
public class StationScooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scooter_id")
    private Long scooterId;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    public StationScooter() {
        super();
    }

    public StationScooter(Station station, Long scooterId) {
        this.station = station;
        this.scooterId = scooterId;
    }
}
