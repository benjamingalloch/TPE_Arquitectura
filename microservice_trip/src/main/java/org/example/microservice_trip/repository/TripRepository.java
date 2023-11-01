package org.example.microservice_trip.repository;

import org.example.microservice_trip.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("tripRepository")
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findAllByUserIdAndScooterId(long userId, long tripId);

    @Query("SELECT (t.endTime - t.startTime) / (1000 * 60) " +
            "FROM Trip t " +
            "WHERE t.id = :id")
    Integer getTripDurationInMinutes(long id);


}
