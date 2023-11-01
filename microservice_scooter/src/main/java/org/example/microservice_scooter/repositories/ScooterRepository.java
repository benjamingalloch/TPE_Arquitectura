package org.example.microservice_scooter.repositories;

import org.example.microservice_scooter.dtos.ScooterKilometersReportDTO;
import org.example.microservice_scooter.entities.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository ("scooterRepository")
public interface ScooterRepository extends JpaRepository<Scooter, Long> {
    List<Scooter> findAllByOrderByKilometersDesc();

    List<Scooter> findAllByOrderByUseTimeDesc();

    @Query(value = "SELECT COUNT(*) FROM Scooter WHERE status = :status", nativeQuery = true)
    long countScootersByStatus( String status);
}