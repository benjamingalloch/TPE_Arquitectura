package org.example.microservice_station.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.microservice_station.dtos.StationDTO;

import java.util.List;

@Entity
@Data
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StationScooter> stationScooters;

    public Station(){
        super();
        // this.scooters = new ArrayList<>();
    }

    public Station(String latitude, String longitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        // this.scooters = new ArrayList<>();
    }

    public Station(StationDTO dto){
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        // this.scooters = new ArrayList<>();
    }
}
