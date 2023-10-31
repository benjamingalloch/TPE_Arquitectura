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
    public TripDTO save(long idUsuario, long idScooter) {
        ResponseEntity<UserDTO> user = restTemplate.getForEntity("http://localhost:8080/usuarios/buscar/" + idUsuario, UserDTO.class);
        ResponseEntity<List<AccountDTO>> accounts = restTemplate.exchange("http://localhost:8080/cuentas/usuario/" + idUsuario,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<AccountDTO>>() {});
        if (accounts.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Error al obtener las cuentas del usuario: " + idUsuario);
        }
        boolean hasCredit = false;
        for(AccountDTO account : accounts.getBody()) {
            if (account.getBalance() > 0)
                hasCredit = true;
        }
        if (!hasCredit) {
            throw new IllegalArgumentException("El usuario no tiene saldo suficiente para realizar un viaje");
        }
        ResponseEntity<ScooterDTO> scooter = restTemplate.getForEntity("http://localhost:8002/monopatines/" + idScooter, ScooterDTO.class);
        if (user.getStatusCode() != HttpStatus.OK || scooter.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("ID de usuario o scooter invalido: " + idUsuario + " " + idScooter);
        }
        TripDTO res = new TripDTO(this.tripRepository.save(new Trip(idUsuario, idScooter, 0, rateRepository.getCurrentFlatRate(), -scooter.getBody().getTiempoDeUso(), -scooter.getBody().getKilometros())));
        UpdateScooterState(idScooter, scooter.getBody());


        //ResponseEntity<?> scooterResponse = restTemplate.postForLocation("http://localhost:8002/scooters/actualizar", scooter.getBody());
        return res;
    }

    private ScooterDTO UpdateScooterState(long idScooter, ScooterDTO scooter) {
        String scooterUpdateUrl = "http://localhost:8002/monopatines/" + idScooter;
        scooter.setStatus("Ocupado");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ScooterDTO> requestEntity = new HttpEntity<>(scooter, headers);

        ResponseEntity<ScooterDTO> scooterResponse = restTemplate.exchange(
                scooterUpdateUrl,
                HttpMethod.PUT,
                requestEntity,
                ScooterDTO.class
        );
        return scooterResponse.getBody();
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
    public void tripEnd(Long id) throws Exception {
        Trip trip = tripRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de estacion invalido: " + id));
        ResponseEntity<ScooterDTO> scooter = restTemplate.getForEntity("http://localhost:8002/scooters/buscar/" + trip.getScooterId(), ScooterDTO.class);
        trip.setPauseTime(scooter.getBody().getPauseTime());
        trip.setUseTime(scooter.getBody().getUseTime());
        trip.setKilometers(scooter.getBody().getKilometers());
        trip.setEndTime(new Timestamp(System.currentTimeMillis()));
        //trip.setScooterEndKms(scooter.getBody().getKilometers());
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
	
	
	/*
	@Transactional(readOnly = true)
	public List<InformeTripDTO> informeTrips() {
		return this.inscriptos.informeTrips();
	}

	@Transactional(readOnly = true)
	public List<InformeTripCantEstudiantesDTO> tripsOrdenadas() {
		return this.tripRepository.tripsOrdenadas();
	} */
}