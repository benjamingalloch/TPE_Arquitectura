package org.example.microservice_scooter.services;

import lombok.AllArgsConstructor;
import org.example.microservice_scooter.dtos.ScooterDTO;
import org.example.microservice_scooter.dtos.ScooterKilometersReportDTO;
import org.example.microservice_scooter.dtos.ScooterUseTimeReportDTO;
import org.example.microservice_scooter.entities.Pause;
import org.example.microservice_scooter.entities.Scooter;
import org.example.microservice_scooter.repositories.PauseRepository;
import org.example.microservice_scooter.repositories.ScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service("scooterService")
@AllArgsConstructor
public class ScooterService{

    @Autowired
    private ScooterRepository scooterRepository;

    @Autowired
    private PauseRepository pauseRepository;

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    //------------------------------------------------------------ PUNTO 3 C ------------------------------------------------------------
    public List<ScooterDTO> findScootersByYear(int year, int minimTrips) throws Exception {
        String scooterUrl = "http://localhost:8084/viajes/monopatines/año/" + year + "/minimos-viajes/" + minimTrips;

        ResponseEntity<List<ScooterDTO>> responseEntity = restTemplate.exchange(
                scooterUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ScooterDTO>>() {}
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new Exception("Error al obtener monopatines del servicio trips");
        }

    }

    @Transactional(readOnly = true)
    public List<ScooterDTO> findAll() {
        return this.scooterRepository.findAll().stream().map(ScooterDTO::new ).toList();
    }
    
    @Transactional(readOnly = true)
    public ScooterDTO findById(Long id) {
        return this.scooterRepository.findById(id).map(ScooterDTO::new).orElseThrow(
                () -> new IllegalArgumentException("El ID de monopatin " + id + " es invalido"));
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
    public List<ScooterKilometersReportDTO> findByKilometers() {
        return this.scooterRepository.findAllByOrderByKilometersDesc().stream().map(ScooterKilometersReportDTO::new ).toList();
    }

    @Transactional
    public List<ScooterUseTimeReportDTO> findByUseTime() {
        return this.scooterRepository.findAllByOrderByUseTimeDesc().stream().map(ScooterUseTimeReportDTO::new ).toList();
    }

    @Transactional
    public void startPause(long scooterId, long tripId, long userId) {
        Scooter scooter = scooterRepository.findById(scooterId).orElseThrow(
                () -> new IllegalArgumentException("El ID de monopatin " + scooterId + " es invalido"));

        Pause activePause = scooter.getPause(tripId, userId);//Si hay una pausa activa, se puede reanudar
        if (activePause != null) {
            if (activePause.getTime() < 15) {
                activePause.setEndTime(null);//Se setea en null para indicar que esa pausa se esta reanudando
                scooterRepository.save(scooter);
            } else {
                throw new IllegalArgumentException("Limite de pausa alcanzado.");
            }
        } else {
            Pause pause = new Pause(scooter, userId, tripId);
            scooter.addPause(pause);
            scooterRepository.save(scooter);
        }
    }
  
    @Transactional
    public void endPause(long scooterId, long tripId, long userId) {
        Scooter scooter = scooterRepository.findById(scooterId).orElseThrow(
                () -> new IllegalArgumentException("El ID de monopatin " + scooterId + " es invalido"));
        Pause activePause = scooter.getPause(tripId, userId);
        if (activePause != null) {
            activePause.endPause();
            scooterRepository.save(scooter);
        }
    }
  
    @Transactional
    public void enableScooter(long id) {
        Scooter scooter = scooterRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de scooter invalido: " + id));
        scooter.setStatus("FREE");
        scooterRepository.save(scooter);
    }
  
    @Transactional
    public void disableScooter(long id) {
        Scooter scooter = scooterRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de scooter invalido: " + id));
        scooter.setStatus("OUT OF SERVICE");
        scooterRepository.save(scooter);
    }


}
