package org.example.microservice_trip.repository;

import org.example.microservice_trip.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("tripRepository")
public interface TripRepository extends JpaRepository<Trip, Long> {
}
