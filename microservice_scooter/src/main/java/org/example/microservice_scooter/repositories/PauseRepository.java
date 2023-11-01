package org.example.microservice_scooter.repositories;

import org.example.microservice_scooter.entities.Pause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pauseRepository")
public interface PauseRepository extends JpaRepository<Pause, Long> {
}
