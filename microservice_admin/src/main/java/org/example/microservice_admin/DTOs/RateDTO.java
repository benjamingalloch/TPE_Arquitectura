package org.example.microservice_admin.DTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class RateDTO {
    private LocalDate date;
    private double normalRate;
    private double pauseRate;

    public RateDTO(RateDTO rate) {
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
