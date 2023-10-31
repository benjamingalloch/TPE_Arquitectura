package org.example.microservice_admin.Repositories;

import org.example.microservice_admin.DTOs.WorkerDTO;
import org.example.microservice_admin.Entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("workerRepository")
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    @Query("SELECT a FROM Worker a WHERE a.role = :role")
    Collection<Worker> findByRol(String role);

}
