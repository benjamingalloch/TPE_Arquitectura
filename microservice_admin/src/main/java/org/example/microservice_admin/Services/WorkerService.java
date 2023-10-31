package org.example.microservice_admin.Services;

import org.example.microservice_admin.DTOs.WorkerDTO;
import org.example.microservice_admin.Entities.Worker;
import org.example.microservice_admin.Repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("workerService")

public class WorkerService {
    @Autowired
    private WorkerRepository workerRepository;

    @Transactional(readOnly = true)
    public List<WorkerDTO> findAll() {
        return this.workerRepository.findAll().stream().map(WorkerDTO::new ).toList();
    }

    @Transactional(readOnly = true)
    public WorkerDTO findById(Long id) {
        return this.workerRepository.findById(id).map(WorkerDTO::new).orElseThrow(
                () -> new IllegalArgumentException("ID de integrante invalido: " + id));
    }

    @Transactional
    public WorkerDTO save(WorkerDTO entity) {
        return new WorkerDTO(this.workerRepository.save(new Worker(entity)));
    }

    @Transactional
    public void delete(Long id) {
        workerRepository.delete(workerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de integrante invalido: " + id)));
    }

    @Transactional
    public void update(Long id, WorkerDTO entity) {
        Worker admin = workerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de integrante invalido: " + id));
        admin.setName(entity.getName());
        admin.setSurname(entity.getSurname());
        admin.setPhoneNumber(entity.getPhoneNumber());
        admin.setEmail(entity.getEmail());
        admin.setRole(entity.getRole());
        workerRepository.save(admin);
    }

    public List<WorkerDTO> findByRol(String rol) {
        return this.workerRepository.findByRol(rol).stream().map(WorkerDTO::new).toList();
    }

}
