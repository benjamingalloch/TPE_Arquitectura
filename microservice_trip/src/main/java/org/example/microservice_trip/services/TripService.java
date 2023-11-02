package org.example.microservice_trip.services;

import org.example.microservice_trip.dtos.*;
import org.example.microservice_trip.entities.Rate;
import org.example.microservice_trip.entities.Trip;
import org.example.microservice_trip.repository.RateRepository;
import org.example.microservice_trip.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("tripService")
public class TripService{
    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Transactional(readOnly = true)
    public List<TripDTO> findAll() {
        return this.tripRepository.findAll().stream().map(TripDTO::new ).toList();
    }

    @Transactional(readOnly = true)
    public TripDTO findById(long id) {
        return this.tripRepository.findById(id).map(TripDTO::new ).orElseThrow(
                () -> new IllegalArgumentException("ID de estacion invalido: " + id));
    }

    @Transactional
    public void delete(Long id) {
        tripRepository.delete(tripRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de viaje invalido: " + id)));
    }

    @Transactional
    public void update(Long id, TripDTO tripDTO) {
        Trip trip = tripRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de viaje invalido: " + id));
        trip.updateFromDTO(tripDTO);
        tripRepository.save(trip);
    }

    @Transactional
    public TripDTO startTrip(long userId, long scooterId) {
        //Verificar si el usuario existe
        ResponseEntity<UserDTO> user = restTemplate.getForEntity("http://localhost:8080/usuarios/" + userId, UserDTO.class);
        if (user.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("ID de usuario invalido: " + userId);
        }

        //Verificar si posee cuentas
        ResponseEntity<List<AccountDTO>> accounts = restTemplate.exchange("http://localhost:8080/cuentas/usuario/" + userId,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<AccountDTO>>() {});
        if (accounts.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error al obtener las cuentas del usuario con id: " + userId);
        }

        //Si el usuario no cuenta con saldo no se puede registrar el viaje
        for(AccountDTO account : Objects.requireNonNull(accounts.getBody())) {
            if (account.getBalance() <= 0) {
                throw new IllegalArgumentException("El usuario no tiene saldo suficiente para realizar el viaje");
            }
        }

        //Verificar si el monopatin existe
        ResponseEntity<ScooterDTO> scooter = restTemplate.getForEntity("http://localhost:8082/monopatines/" + scooterId, ScooterDTO.class);
        if (scooter.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("ID monopatin invalido: " + scooterId);
        }

        //Verificar si el viaje no esta vigente
        List<Trip> tripsOfUserAndScooter = this.tripRepository.findAllByUserIdAndScooterId(userId, scooterId);
        if (!tripsOfUserAndScooter.isEmpty() && (tripsOfUserAndScooter.get(tripsOfUserAndScooter.size() - 1).getEndTime() == null)) {
            throw new IllegalArgumentException("El viaje ya existe y tiene id: " + tripsOfUserAndScooter.get(tripsOfUserAndScooter.size() - 1).getId());
        }

        //Creamos un viaje con algunos valores default
        Trip trip = new Trip(userId, scooterId, Timestamp.from(Instant.now()),
                null,
                0,
                0.0,
                0);
        this.tripRepository.save(trip);

        //Actualizamos el estado del monopatin a BUSY
        ResponseEntity<?> responseEntity = updateScooterState(scooterId, Objects.requireNonNull(scooter.getBody()), "BUSY");
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("No se pudo cambiar el estado del monopatin a BUSY");
        }

        return new TripDTO(trip);
    }

    @Transactional
    public TripDTO endTrip(long tripId, double kilometers) throws Exception {
        //Verificar que el viaje existe
        Trip trip = tripRepository.findById(tripId).orElseThrow(
                () -> new IllegalArgumentException("No se encontro un viaje con id: " + tripId));

        //Obtengo el monopatin
        ResponseEntity<ScooterDTO> scooterDTOResponseEntity = restTemplate.getForEntity("http://localhost:8082/monopatines/" + trip.getScooterId(), ScooterDTO.class);

        //Terminamos la pausa si es que se encuentra activa
         ResponseEntity<?> r1 = restTemplate.exchange("http://localhost:8082/monopatines/"
                        + trip.getScooterId()
                        + "/despausar/viaje/"
                        + trip.getId()
                        + "/usuario/"
                        + trip.getUserId(),
                HttpMethod.PUT,
                null,
                ScooterDTO.class
        );
        if (r1.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error al finalizar la pausa del monopatin con id: " + trip.getScooterId());
        }

        //Actualizamos los km del monopatin
        Objects.requireNonNull(scooterDTOResponseEntity.getBody())
                .setKilometers(scooterDTOResponseEntity
                        .getBody().getKilometers() + kilometers);
        ResponseEntity<?> r2 = restTemplate.exchange("http://localhost:8082/monopatines/"
                        + trip.getScooterId(),
                HttpMethod.POST,
                scooterDTOResponseEntity,
                ScooterDTO.class
        );
        if (r2.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("No se pudo actualizar los km del monopatin con id: " + trip.getScooterId());
        }

        //Actualizamos el estado del monopatin a FREE
        ResponseEntity<?> responseEntity = updateScooterState(trip.getScooterId(), Objects.requireNonNull(scooterDTOResponseEntity.getBody()), "FREE");
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("No se pudo cambiar el estado del monopatin a FREE. ");
        }

        List<PauseDTO> scooterPauses = Objects.requireNonNull(scooterDTOResponseEntity.getBody()).getPauses();
        if (!scooterPauses.isEmpty()){
            //Setea el tiempo de la pausa
            trip.setPauseTime(scooterDTOResponseEntity.getBody().getPauses().get(scooterPauses.size() - 1).getTime());
        }

        trip.setEndTime(Timestamp.from(Instant.now()));
        trip.setKilometers(kilometers);

        //Calcular precio
        double price;
        int duration = this.tripRepository.getTripDurationInMinutes(tripId);
        if (trip.getPauseTime() > 0) {
            price = (this.rateRepository.getCurrentNormalRate() * (duration - trip.getPauseTime()))//Tarifa normal
                    + (this.rateRepository.getCurrentPauseRate() * trip.getPauseTime());//Tarifa en pausa
        } else {
            price = this.rateRepository.getCurrentNormalRate() * (duration - trip.getPauseTime());//Tarifa normal
        }
        trip.setPrice(price);

        //------------------------COBRAR CON ADMINISTRACION--------------------------

        return new TripDTO(tripRepository.save(trip));
    }

    private ResponseEntity<?> updateScooterState(long scooterId, ScooterDTO scooter, String status) {
        scooter.setStatus(status);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ScooterDTO> requestEntity = new HttpEntity<>(scooter, headers);

        return restTemplate.exchange("http://localhost:8082/monopatines/" + scooterId,
                HttpMethod.PUT,
                requestEntity,
                ScooterDTO.class
        );
    }

    @Transactional
    public Double getCurrentNormalRate() {
        return rateRepository.getCurrentNormalRate();
    }

    @Transactional
    public Double getCurrentPauseRate() {
        return rateRepository.getCurrentPauseRate();
    }

    @Transactional
    public RateDTO addRatesForDate(RateDTO rateDTO) {
        if(this.rateRepository.existsRateByDate(rateDTO.getDate())){
            throw new IllegalArgumentException("Ya existen tarifas para la fecha: " + rateDTO.getDate());
        }
        Rate rate = new Rate(rateDTO);
        return new RateDTO(this.rateRepository.save(rate));
    }
}