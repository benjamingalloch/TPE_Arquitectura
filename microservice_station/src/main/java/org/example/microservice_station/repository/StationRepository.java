package org.example.microservice_station.repository;

import org.example.microservice_station.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stationRepository")
public interface StationRepository extends JpaRepository<Station, Long> {
}