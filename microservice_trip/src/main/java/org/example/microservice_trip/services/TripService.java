package org.example.microservice_trip.services;

import org.example.microservice_trip.dtos.AccountDTO;
import org.example.microservice_trip.dtos.ScooterDTO;
import org.example.microservice_trip.dtos.TripDTO;
import org.example.microservice_trip.dtos.UserDTO;
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
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    public TripDTO findById(Long id) {
        return this.tripRepository.findById(id).map(TripDTO::new).orElseThrow(
                () -> new IllegalArgumentException("ID de estacion invalido: " + id));
    }

    @Transactional
    public TripDTO save(long userId, long scooterId) {
        ResponseEntity<UserDTO> user = restTemplate.getForEntity("http://localhost:8080/usuarios/" + userId, UserDTO.class);
        if (user.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("ID de usuario invalido: " + userId);
        }

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

        ResponseEntity<ScooterDTO> scooter = restTemplate.getForEntity("http://localhost:8082/monopatines/" + scooterId, ScooterDTO.class);
        if (scooter.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("ID monopatin invalido: " + scooterId);
        }

        Trip trip = new Trip(userId, scooterId, Timestamp.from(Instant.now()), null,0, 0.0, 0, 0);
        this.tripRepository.save(trip);

        ResponseEntity<?> responseEntity = updateScooterState(scooterId, Objects.requireNonNull(scooter.getBody()));
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("No se pudo cambiar el estado del monopatin a BUSY");
        }

        return new TripDTO(trip);
    }

    private ResponseEntity<?> updateScooterState(long scooterId, ScooterDTO scooter) {
        scooter.setStatus("BUSY");

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
    public Double getCurrentFlatRate() {
        return rateRepository.getCurrentFlatRate();
    }

    @Transactional
    public Double getCurrentFullRate() {
        return rateRepository.getCurrentFullRate();
    }

    @Transactional
    public void endTrip(Long id, double kilometers) throws Exception {
        Trip trip = tripRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No se encontro un viaje con id: " + id));

        ResponseEntity<ScooterDTO> scooter = restTemplate.getForEntity("http://localhost:8082/monopatines/" + trip.getScooterId(), ScooterDTO.class);

        trip.setKilometers(kilometers);
        trip.setEndTime(Timestamp.from(Instant.now()));
        trip.setUseTime((int) ChronoUnit.MINUTES.between(
                trip.getStartTime().toInstant(),
                trip.getEndTime().toInstant())
        );

        if (trip.getPauseTime() == 0) {
            trip.setRate(scooter.getBody().getUseTime() * getCurrentFlatRate());//esto tiene que ser con el tiempo
        }
        else {
            trip.setRate( scooter.getBody().getUseTime() * getCurrentFlatRate() + scooter.getBody().getPauseTime() * getCurrentFullRate());
        }

        tripRepository.save(trip);
        scooter.getBody().setStatus("Libre");
        updateUserAccount(trip.getUserId(), trip.getRate());
        URI scooterResponse = restTemplate.postForLocation("http://localhost:8002/scooters/actualizar", scooter.getBody());
    }

    @Transactional
    public void updateUserAccount(long userId, double rate) throws Exception {
        List<AccountDTO> accounts = getUserAccounts(userId);
        AccountDTO account = accounts.stream().filter(a -> a.getBalance() >= rate && a.isAvailable()).findFirst().orElse(null);
        if (Objects.isNull(account)) { //TODO: ver que hacer si no hay cuentas con saldo suficiente
            throw new IllegalArgumentException("No hay cuentas con saldo suficiente para el usuario: " + userId);
        }
        account.setBalance(account.getBalance() - rate);
        try {
            updateAccountBalance(account);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al actualizar la cuenta del usuario: " + userId);
        }
    }

    @Transactional(readOnly = true)
    public List<AccountDTO> getUserAccounts(long userId) throws Exception {
        String url = "localhost:8080/usuarios/cuentas/" + userId;
        ResponseEntity<AccountDTO[]> response = restTemplate.getForEntity(url, AccountDTO[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(response.getBody());
        } else {
            throw new Exception("Error al obtener las cuentas del usuario");
        }
    }

    @Transactional
    public void updateAccountBalance(AccountDTO account) throws Exception {
        String accountUrl = "http://localhost:8080/usuarios/actualizar/" + account.getAccountId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AccountDTO> requestEntity = new HttpEntity<>(account, headers);

        ResponseEntity<Void> response = restTemplate.exchange(accountUrl, HttpMethod.PUT, requestEntity, Void.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Error al actualizar la cuenta del usuario");
        }
    }


    @Transactional
    public void delete(Long id) {
        tripRepository.delete(tripRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de viaje invalido: " + id)));
    }

    @Transactional
    public void update(Long id, TripDTO entity) {
        Trip trip = tripRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de estacion invalido: " + id));
        trip.setUserId(entity.getUserId());
        trip.setScooterId(entity.getScooterId());
        trip.setRate(entity.getRate());
        trip.setEndTime(entity.getEndTime());
        tripRepository.save(trip);
    }
}