package org.example.microservice_admin.Services;

import org.example.microservice_admin.DTOs.NewScooterDTO;
import org.example.microservice_admin.DTOs.ScooterDTO;
import org.example.microservice_admin.DTOs.ScooterKilometersReportDTO;
import org.example.microservice_admin.DTOs.StationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service("adminService")
public class AdminService{
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

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
            throw new Exception("Error al obtener monopatines");
        }
    }
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
        String stationUrl = "http://localhost:8002/monopatines/eliminar/" + scooterId;

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

    @Transactional
    public ResponseEntity<?> suspendAccount(long accountId) throws Exception {
        String accountUrl = "http://localhost:8003/cuentas/suspender/" + accountId;

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
        String accountUrl = "http://localhost:8003/cuentas/activar/" + accountId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StationDTO> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(accountUrl, HttpMethod.PATCH, requestEntity, Void.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Error al activar la cuenta " + accountId);
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

