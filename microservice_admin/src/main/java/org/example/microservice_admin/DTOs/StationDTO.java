package org.example.microservice_admin.DTOs;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StationDTO {
    private String latitude;
    private String longitude;

    public StationDTO(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
