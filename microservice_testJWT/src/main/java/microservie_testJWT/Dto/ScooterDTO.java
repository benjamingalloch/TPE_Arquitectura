package microservie_testJWT.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScooterDTO {
    private long id;
    private double latitude;
    private double longitude;
    private String status;
    private double kilometers;
    private int useTime;

    public ScooterDTO(double latitude, double longitude, String status, double kilometers, int useTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.kilometers = kilometers;
        this.useTime = useTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
