package org.example.microservice_trip.repository;

import org.example.microservice_trip.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("RateRepository")

public interface RateRepository extends JpaRepository<Rate, Long> {
    @Query("SELECT value.flatRate FROM Rate WHERE id = (SELECT MAX(id) FROM Rate)")
    Double getCurrentFlatRate();

    @Query("SELECT value.fullRate FROM Rate WHERE id = (SELECT MAX(id) FROM Rate)")
    Double getCurrentFullRate();
}
}
