package org.example.microservice_station.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.microservice_station.entities.Station;

@Getter
@RequiredArgsConstructor
public class StationDTO {
    private String latitude;
    private String longitude;

    public StationDTO(Station station) {
        this.latitude = station.getLatitude();
        this.longitude = station.getLongitude();
    }

    public StationDTO(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
