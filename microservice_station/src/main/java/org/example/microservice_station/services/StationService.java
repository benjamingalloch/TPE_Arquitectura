package org.example.microservice_station.services;

import org.example.microservice_station.dtos.StationDTO;
import org.example.microservice_station.entities.Station;
import org.example.microservice_station.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("stationService")
public class StationService{
    @Autowired
    private StationRepository stationRepository;
    @Transactional(readOnly = true)
    public List<StationDTO> findAll() {
        return this.stationRepository.findAll().stream().map(StationDTO::new ).toList();
    }

    @Transactional(readOnly = true)
    public StationDTO findById(Long id) {
        return this.stationRepository.findById(id).map(StationDTO::new).orElseThrow(
                () -> new IllegalArgumentException("ID de estacion invalido: " + id));
    }

    @Transactional
    public StationDTO save(StationDTO entity) {
        return new StationDTO(this.stationRepository.save(new Station(entity)));
    }

    @Transactional
    public void delete(Long id) {
        stationRepository.delete(stationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de estacion invalido: " + id)));
    }

    @Transactional
    public void update(Long id, StationDTO entity) {
        Station station = stationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de estacion invalido: " + id));
        station.setLatitude(entity.getLatitude());
        station.setLongitude(entity.getLongitude());
        stationRepository.save(station);
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