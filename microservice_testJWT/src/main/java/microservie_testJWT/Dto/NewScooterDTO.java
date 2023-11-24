package microservie_testJWT.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NewScooterDTO {
    private String latitude;
    private String longitude;
    private String status;
    private double kilometers;
    private int useTime;
    private int pauseTime;

    public NewScooterDTO(String latitude, String longitude, String status, double kilometers, int useTime, int pauseTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.kilometers = kilometers;
        this.useTime = useTime;
        this.pauseTime = pauseTime;
    }
}