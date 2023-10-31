package org.example.microservice_station.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.microservice_station.dtos.StationDTO;

@Entity
@Data
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="station_id")
    private long stationId;

    @Column(name = "latitude")
    private String latitude;
    @Column(name = "longitude")
    private String longitude;

    //private List<Scooter> scooters; TODO: ver como hacer la relacion con scooter

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
