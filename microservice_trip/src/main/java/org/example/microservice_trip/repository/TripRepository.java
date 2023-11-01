package org.example.microservice_trip.repository;

import org.example.microservice_trip.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("tripRepository")
public interface TripRepository extends JpaRepository<Trip, Long> {

    boolean existsTripByUserIdAndScooterId(long userId, long tripId);

    Optional<Trip> findByUserIdAndScooterId(long userId, long tripId);
}
