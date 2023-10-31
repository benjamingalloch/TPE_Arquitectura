package org.example.microservice_station.repository;

import org.example.microservice_station.dtos.StationScooterDTO;
import org.example.microservice_station.entities.StationScooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("stationScooterRepository")
public interface StationScooterRepository extends JpaRepository<StationScooter, Long> {

    @Query("SELECT ss " +
            "FROM StationScooter ss " +
            "WHERE ss.scooterId = :scooterId ")
    Optional<StationScooterDTO> findByScooterId(long scooterId);

}
