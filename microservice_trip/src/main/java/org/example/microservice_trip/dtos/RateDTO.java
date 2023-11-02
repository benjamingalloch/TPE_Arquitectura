package org.example.microservice_trip.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.microservice_trip.entities.Rate;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class RateDTO {
    private LocalDate date;
    private double normalRate;
    private double pauseRate;

    public RateDTO(Rate rate) {
        this.date = rate.getDate();
        this.normalRate = rate.getNormalRate();
        this.pauseRate = rate.getPauseRate();
    }

    public RateDTO(double normalRate, double pauseRate, LocalDate date) {
        this.normalRate = normalRate;
        this.pauseRate = pauseRate;
        this.date = date;
    }
}
