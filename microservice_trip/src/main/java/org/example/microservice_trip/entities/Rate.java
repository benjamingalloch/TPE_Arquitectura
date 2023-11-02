package org.example.microservice_trip.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.microservice_trip.dtos.RateDTO;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "rate")
public class Rate {
    @Id
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "normal_rate")
    private double normalRate;

    @Column(name = "pause_rate")
    private double pauseRate;

    public Rate() {
        super();
    }

    public Rate(RateDTO rateDTO){
        this.normalRate = rateDTO.getNormalRate();
        this.pauseRate = rateDTO.getPauseRate();
        this.date = rateDTO.getDate();
    }

    public Rate(double normalRate, double pauseRate, LocalDate date) {
        this.normalRate = normalRate;
        this.pauseRate = pauseRate;
        this.date = date;
    }
}