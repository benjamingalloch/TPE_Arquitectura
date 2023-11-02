package org.example.microservice_admin.Services;

import io.swagger.v3.oas.annotations.Operation;
import org.example.microservice_admin.DTOs.*;
import org.example.microservice_admin.Repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service("adminService")
public class AdminService{
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private BillRepository billRepository;

    @Transactional
    public List<ScooterDTO> findAllScooters() throws Exception {
        String scooterUrl = "http://localhost:8082/monopatines";

        ResponseEntity<List<ScooterDTO>> responseEntity = restTemplate.exchange(
                scooterUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ScooterDTO>>() {}
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new Exception("Error al obtener monopatines en admin service");
        }
    }

    //------------------------------------------------------------ PUNTO 3 B ------------------------------------------------------------
    @Transactional
    public ResponseEntity<?> suspendAccount(long accountId) throws Exception {
        String accountUrl = "http://localhost:8084/cuentas/suspender/" + accountId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StationDTO> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(accountUrl, HttpMethod.PUT, requestEntity, Void.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Error al suspender la cuenta " + accountId);
        }
        return response;
    }

    @Transactional
    public ResponseEntity<?> activateAccount(long accountId) throws Exception {
        String accountUrl = "http://localhost:8084/cuentas/habilitar/" + accountId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StationDTO> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(accountUrl, HttpMethod.PUT, requestEntity, Void.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Error al activar la cuenta " + accountId);
        }
        return response;
    }

    //------------------------------------------------------------ PUNTO 3 C ------------------------------------------------------------
    public List<ScooterDTO> findScootersByYear(int year, int minimTrips) throws Exception {
        String scooterUrl = "http://localhost:8082/monopatines/año/" + year + "/minimos-viajes/" + minimTrips;

        ResponseEntity<List<ScooterDTO>> responseEntity = restTemplate.exchange(
                scooterUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ScooterDTO>>() {}
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new Exception("Error al obtener monopatines del servicio monopatines");
        }
    }

    //------------------------------------------------------------ PUNTO 3 E ------------------------------------------------------------
    public Long countScootersByStatus(String status) throws Exception {
        String scooterUrl = "http://localhost:8082/monopatines/cantidad/" + status;
        return restTemplate.getForObject(scooterUrl, Long.class);
    }


    //------------------------------------------------------------ PUNTO 3 F ------------------------------------------------------------
    public ResponseEntity<?> addNewRate(RateDTO rateDTO) throws Exception {
        String tripUrl = "http://localhost:8085/viajes/agregar-tarifas-desde";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RateDTO> requestEntity = new HttpEntity<>(rateDTO, headers);

        ResponseEntity<Void> response = restTemplate.exchange(tripUrl, HttpMethod.POST, requestEntity, Void.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Error a REST");
        }
        return response;
    }

    //------------------------------------------------------------------------------------------------------------------------------

    @Transactional
    public ResponseEntity<?> saveNewScooter(NewScooterDTO scooterDTO) throws Exception {
        String scooterUrl = "http://localhost:8082/monopatines";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NewScooterDTO> requestEntity = new HttpEntity<>(scooterDTO, headers);

        ResponseEntity<Void> response = restTemplate.exchange(scooterUrl, HttpMethod.POST, requestEntity, Void.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Error al guardar el nuevo monopatin");
        }


        return response;
    }

    @Transactional
    public ResponseEntity<?> deleteScooter(long scooterId) throws Exception {
        String stationUrl = "http://localhost:8002/monopatines/" + scooterId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ScooterDTO> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(stationUrl, HttpMethod.DELETE, requestEntity, Void.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Error al borrar el monopatin " + scooterId);
        }
        return response;
    }

    @Transactional
    public ResponseEntity<?> saveNewStation(StationDTO stationDTO) throws Exception {
        String stationUrl = "http://localhost:8083/estaciones";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<StationDTO> requestEntity = new HttpEntity<>(stationDTO, headers);

        ResponseEntity<Void> response = restTemplate.exchange(stationUrl, HttpMethod.POST, requestEntity, Void.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Error al guardar la nueva estacion");
        }
        return response;
    }

    @Transactional
    public ResponseEntity<?> deleteStation(long stationId) throws Exception {
        String accountUrl = "http://localhost:8083/estaciones/" + stationId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StationDTO> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(accountUrl, HttpMethod.DELETE, requestEntity, Void.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Error borrar la estacion " + stationId);
        }
        return response;
    }





    @Transactional(readOnly = true)
    public List<ScooterKilometersReportDTO> getReportScootersByKms() throws Exception {
        String scooterUrl = "http://localhost:8002/monopatines/reporte/kilometros";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StationDTO> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<ScooterKilometersReportDTO>> response = restTemplate.exchange(scooterUrl,
                HttpMethod.GET,
                requestEntity,
                ParameterizedTypeReference.forType(List.class));
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Error al obtener los datos.");
        }
        return response.getBody();
    }

    @Transactional(readOnly = true)
    public Object enableScooter(Long id) throws Exception {

        String accountUrl = "http://localhost:8082/monopatines/habilitar/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StationDTO> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(accountUrl, HttpMethod.PUT, requestEntity, Void.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Error al habilitar el monopatin con Id: " + id);
        }
        return response;
    }

    @Transactional(readOnly = true)
    public Object disableScooter(Long id) throws Exception {

        String accountUrl = "http://localhost:8082/monopatines/mantenimiento/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StationDTO> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(accountUrl, HttpMethod.PUT, requestEntity, Void.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Error al poner en mantenimiento el monopatin con Id: " + id);
        }
        return response;
    }



}

