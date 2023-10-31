package org.example.microservice_station.services;

import org.example.microservice_station.dtos.ScooterDTO;
import org.example.microservice_station.dtos.StationDTO;
import org.example.microservice_station.dtos.StationScooterDTO;
import org.example.microservice_station.entities.Station;
import org.example.microservice_station.entities.StationScooter;
import org.example.microservice_station.repository.StationRepository;
import org.example.microservice_station.repository.StationScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service("stationService")
public class StationService{

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationScooterRepository stationScooterRepository;

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Transactional(readOnly = true)
    public List<StationDTO> findAll() {
        return this.stationRepository.findAll().stream().map(StationDTO::new ).toList();
    }

    @Transactional(readOnly = true)
    public StationDTO findById(Long id) {
        return this.stationRepository.findById(id).map(StationDTO::new).orElseThrow(
                () -> new IllegalArgumentException("El ID de estacion " + id + " es invalido"));
    }

    @Transactional
    public StationDTO save(StationDTO stationDTO) {
        return new StationDTO(this.stationRepository.save(new Station(stationDTO)));
    }

    @Transactional
    public void delete(Long id) {
        stationRepository.delete(stationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("El ID de estacion " + id + " es invalido")));
    }

    @Transactional
    public void update(Long id, StationDTO stationDTO) {
        Station station = stationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("El ID de estacion " + id + " es invalido"));
        station.setLatitude(stationDTO.getLatitude());
        station.setLongitude(stationDTO.getLongitude());
        stationRepository.save(station);
    }

    @Transactional
    public void addScooter(Long stationId, Long scooterId) {

        // Busco la estacion correspondiente
        Station station = stationRepository.findById(stationId).orElseThrow(
                () -> new IllegalArgumentException("La estacion con Id " + stationId + " no es valida"));

        // Traigo el monopatin con el Id
        ResponseEntity<ScooterDTO> scooter = restTemplate.getForEntity("http://localhost:8082/monopatines/" + scooterId, ScooterDTO.class);

        // Chequeo que el haya venido un monopatin
        if (scooter.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("El monopatin con Id " + scooterId + " no es valido");
        }

        // Chequeo que no se encuetre en otra estacion
        Optional<StationScooterDTO> stationScooterDTO = stationScooterRepository.findByScooterId(scooterId);
        if (stationScooterDTO.isPresent()) {
            throw new IllegalArgumentException("El monopatin con Id " + scooterId + " se encuentra en la estacion con Id: " + stationScooterDTO.get().getStationId());
        }

        // Agrego el monopatin a la estacion
        stationScooterRepository.save(new StationScooter(station, scooterId));
    }

    @Transactional
    public void removeScooter(Long stationId, Long scooterId) {

        // Busco la estacion correspondiente
        Station station = stationRepository.findById(stationId).orElseThrow(
                () -> new IllegalArgumentException("La estacion con Id " + stationId + " no es valida"));

        // Traigo el monopatin con el Id
        ResponseEntity<ScooterDTO> scooter = restTemplate.getForEntity("http://localhost:8082/monopatines/" + scooterId, ScooterDTO.class);

        // Chequeo que el haya venido un monopatin
        if (scooter.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("El monopatin con Id " + scooterId + " no es valido");
        }

        // Busco en que estacion esta
        Optional<StationScooterDTO> stationScooterDTO = stationScooterRepository.findByScooterId(scooterId);

        // Chequeo que este en la estacion
        if (stationScooterDTO.isPresent()) {
            if (stationScooterDTO.get().getStationId().equals(stationId)) {
                // Lo elimino
                stationScooterRepository.delete(new StationScooter(station, scooterId));
                throw new IllegalArgumentException("okkk");
            } else {
                throw new IllegalArgumentException("El monopatin con Id " + scooterId + " se encuentra en otra estacion con Id: " + stationScooterDTO.get().getStationId());
            }
        } else {
            throw new IllegalArgumentException("El monopatin con Id " + scooterId + " no se encuentra en ninguna estacion");
        }
    }


	/*
	@Transactional(readOnly = true)
	public List<InformeStationDTO> informeStations() {
		return this.inscriptos.informeStations();
	}

	@Transactional(readOnly = true)
	public List<InformeStationCantEstudiantesDTO> stationsOrdenadas() {
		return this.stationRepository.stationsOrdenadas();
	} */
}