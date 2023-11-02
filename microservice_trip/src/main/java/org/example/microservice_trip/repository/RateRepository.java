package org.example.microservice_trip.repository;

import org.example.microservice_trip.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository("RateRepository")
public interface RateRepository extends JpaRepository<Rate, Long> {

    @Query("SELECT r.normalRate " +
            "FROM Rate r " +
            "WHERE r.date <= CURRENT_DATE() " +
            "ORDER BY ABS(DATEDIFF(CURRENT_DATE(), r.date)) ASC " +
            "LIMIT 1")
    Double getCurrentNormalRate();

    @Query("SELECT r.pauseRate " +
            "FROM Rate r " +
            "WHERE r.date <= CURRENT_DATE() " +
            "ORDER BY ABS(DATEDIFF(CURRENT_DATE(), r.date)) ASC " +
            "LIMIT 1")
    Double getCurrentPauseRate();

    boolean existsRateByDate(LocalDate date);
}

