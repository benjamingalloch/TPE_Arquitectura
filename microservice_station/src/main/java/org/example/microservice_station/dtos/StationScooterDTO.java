package org.example.microservice_station.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.microservice_station.entities.StationScooter;

@Getter
@RequiredArgsConstructor
public class StationScooterDTO {

    private Long id;
    private Long stationId;
    private Long scooterId;

    public StationScooterDTO(Long id, Long stationId, Long scooterId) {
        this.id = id;
        this.stationId = stationId;
        this.scooterId = scooterId;
    }

    public StationScooterDTO(StationScooter stationScooter) {
        this.id = stationScooter.getId();
        this.stationId = stationScooter.getStation().getId();
        this.scooterId = stationScooter.getScooterId();
    }
}
