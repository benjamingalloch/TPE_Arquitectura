package microservie_testJWT.Services;

import microservie_testJWT.Dto.NewScooterDTO;
import microservie_testJWT.Dto.ScooterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AdminService {
	
	@Autowired
	RestTemplate restTemplate;

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
		String stationUrl = "http://localhost:8082/monopatines/" + scooterId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ScooterDTO> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<Void> response = restTemplate.exchange(stationUrl, HttpMethod.DELETE, requestEntity, Void.class);
		if (response.getStatusCode() != HttpStatus.OK) {
			throw new Exception("Error al borrar el monopatin " + scooterId);
		}
		return response;
	}

	@Transactional(readOnly = true)
	public Object enableScooter(Long id) throws Exception {

		String accountUrl = "http://localhost:8082/monopatines/habilitar/" + id;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ScooterDTO> requestEntity = new HttpEntity<>(headers);

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
		HttpEntity<ScooterDTO> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<Void> response = restTemplate.exchange(accountUrl, HttpMethod.PUT, requestEntity, Void.class);
		if (response.getStatusCode() != HttpStatus.OK) {
			throw new Exception("Error al poner en mantenimiento el monopatin con Id: " + id);
		}
		return response;
	}

	public ScooterDTO findScooterById(long scooterId)  throws Exception {
		String stationUrl = "http://localhost:8082/monopatines/" + scooterId;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ScooterDTO> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<ScooterDTO> response = restTemplate.exchange(stationUrl, HttpMethod.GET, requestEntity, ScooterDTO.class);
		if (response.getStatusCode() != HttpStatus.OK) {
			throw new Exception("Error al obtener el monopatin " + scooterId);
		} else {
			return response.getBody();
		}

	}

}
