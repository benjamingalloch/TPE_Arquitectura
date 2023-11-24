package microservie_testJWT.Controllers;

import microservie_testJWT.Dto.NewScooterDTO;
import microservie_testJWT.Security.Model.AuthResponse;
import microservie_testJWT.Security.Model.LoginRequest;
import microservie_testJWT.Services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import microservie_testJWT.Services.AdminService;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private final AuthService authService;


	@Operation(description = "Trae todos los monopatines")
	@GetMapping("/monopatines")
	public ResponseEntity<?> getAllScooters() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.findAllScooters());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
		}
	}

	@Operation(description = "Trae un monopatin por su id")
	@GetMapping("/monopatines/{scooterId}")
	public ResponseEntity<?> getScooterById(@PathVariable long scooterId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.findScooterById(scooterId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
		}
	}


	//------------------------------------------------------------ PUNTO 3 C ------------------------------------------------------------
	@Operation(description = "Trae todos los monopatines con x viajes en j año")
	@GetMapping("/monopatines/año/{year}/minimos-viajes/{minimTrips}")
	public ResponseEntity<?> getScootersByYear(@PathVariable int year, @PathVariable int minimTrips) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.findScootersByYear(year, minimTrips));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener monopatines " + e.getMessage());
		}
	}

	//------------------------------------------------------------ PUNTO 3 E ------------------------------------------------------------
	@Operation(description = "Trae la cantidad de monopatines por estado")
	@GetMapping("/monopatines/cantidad/{status}")
	public ResponseEntity<?> countScootersByStatus(@PathVariable String status) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.countScootersByStatus(status));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
		}
	}

	@Operation(description = "Crea nuevo monopatin")
	@PostMapping("/monopatines")
	public ResponseEntity<?> save(@RequestBody NewScooterDTO scooterDTO) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.saveNewScooter(scooterDTO));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
		}
	}

	@Operation(description = "Elimina monopatin por Id")
	@DeleteMapping("/monopatines/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteScooter(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errorr " + e.getMessage());
		}
	}

	@Operation(description = "Habilitar monopatin mediante Id")
	@PutMapping ("/monopatines/{id}/habilitar")
	public ResponseEntity<?> enableScooter(@PathVariable Long id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.enableScooter(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
		}
	}

	@Operation(description = "Poner en mantenimiento monopatin mediante Id")
	@PutMapping("/monopatines/{id}/mantenimiento")
	public ResponseEntity<?> disableScooter(@PathVariable Long id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(adminService.disableScooter(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestParam("username") String username, @RequestParam("password") String password){
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername(username);
		loginRequest.setPassword(password);
		return ResponseEntity.ok(authService.login(loginRequest));
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestParam("username") String username, @RequestParam("password") String password){
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername(username);
		loginRequest.setPassword(password);
		return ResponseEntity.ok(authService.register(loginRequest));
	}

}
