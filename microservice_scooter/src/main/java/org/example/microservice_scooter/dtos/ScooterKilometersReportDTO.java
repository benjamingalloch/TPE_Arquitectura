package org.example.microservice_scooter.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.microservice_scooter.entities.Scooter;


    @Getter
    @RequiredArgsConstructor
    public class ScooterKilometersReportDTO {
        private long id;
        private double kilometers;
        private int useTime;

        public ScooterKilometersReportDTO(Scooter scooter) {
            this.id = scooter.getId();
            this.kilometers = scooter.getKilometers();
            this.useTime = scooter.getUseTime();
        }

        public ScooterKilometersReportDTO(long id, double kilometers, int useTime) {
            this.id = id;
            this.kilometers = kilometers;
            this.useTime = useTime;
        }

    }
