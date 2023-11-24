package microservie_testJWT.Dto;

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
