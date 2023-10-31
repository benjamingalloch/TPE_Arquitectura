package org.example.microservice_scooter.services;

import lombok.AllArgsConstructor;
import org.example.microservice_scooter.dtos.ScooterDTO;
import org.example.microservice_scooter.dtos.ScooterKilometersReportDTO;
import org.example.microservice_scooter.dtos.ScooterUseTimeReportDTO;
import org.example.microservice_scooter.entities.Scooter;
import org.example.microservice_scooter.repositories.ScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("scooterService")
@AllArgsConstructor
public class ScooterService{

    @Autowired
    private ScooterRepository scooterRepository;

    @Transactional(readOnly = true)
    public List<ScooterDTO> findAll() {
        return this.scooterRepository.findAll().stream().map(ScooterDTO::new ).toList();
    }

    @Transactional(readOnly = true)
    public ScooterDTO findById(Long id) {
        return this.scooterRepository.findById(id).map(ScooterDTO::new).orElseThrow(
                () -> new IllegalArgumentException("ID de monopatin invalido: " + id));
    }

    @Transactional
    public ScooterDTO save(ScooterDTO scooterDTO) {
        return new ScooterDTO(this.scooterRepository.save(new Scooter(scooterDTO)));
    }

    @Transactional
    public void delete(Long id) {
        scooterRepository.delete(scooterRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de monopatin invalido: " + id)));
    }

    @Transactional
    public void update(Long id, ScooterDTO scooterDTO) {
        Scooter scooter = scooterRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de monopatin invalido: " + id));
        scooter.updateFromDTO(scooterDTO);
        scooterRepository.save(scooter);
    }

    @Transactional
    public List<ScooterKilometersReportDTO> findByKilometers(){
        return this.scooterRepository.findAllByOrderByKilometersDesc().stream().map(ScooterKilometersReportDTO::new ).toList();
    }

    @Transactional
    public List<ScooterUseTimeReportDTO> findByUsedTime(){
        return this.scooterRepository.findAllByOrderByUseTimeDesc().stream().map(ScooterUseTimeReportDTO::new ).toList();
    }
}
