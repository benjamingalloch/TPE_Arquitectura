package org.example.microservice_admin.Services;

import org.example.microservice_admin.DTOs.ScooterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service("maintenanceService")
public class MaintenanceService{
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public void updateScooterState(long idScooter, String estado) {
        ResponseEntity<ScooterDTO> scooter = restTemplate.getForEntity("http://localhost:8002/monopatines/buscar/" + idScooter, ScooterDTO.class);
        if (scooter.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("ID de scooter invalido: " + idScooter);
        }

        HttpEntity<ScooterDTO> requestEntity;
        ScooterDTO scooterDTO = scooter.getBody();
        if (scooterDTO != null) {
            scooterDTO.setStatus(estado);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            requestEntity = new HttpEntity<>(scooterDTO, headers);
        } else {
            throw new RuntimeException("El cuerpo es nulo.");
        }

        try {
            ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8002/monopatines/actualizar/", HttpMethod.PUT, requestEntity, Void.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new Exception("Error al actualizar el estado del monopatin" + idScooter);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el estado del monopatin. ", e);
        }
    }



}