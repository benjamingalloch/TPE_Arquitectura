package org.example.microservice_admin.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.example.microservice_admin.DTOs.NewScooterDTO;
import org.example.microservice_admin.DTOs.StationDTO;
import org.example.microservice_admin.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administracion")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(description = "Trae todos los monopatines")
    @GetMapping("/monopatines")
    public ResponseEntity<?> getAllScooters() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.findAllScooters());
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }

    @Operation(description = "Obtiene informe de monopatines ordenados por kilometros recorridos") //??
    @GetMapping("/informes/reporteDeMonopatinesPor/KilometrosRecorridos")
    public ResponseEntity<?> getKilometros() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.getReportScootersByKms());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }

    @Operation(description = "Crea nueva parada")
    @PostMapping("/estaciones/nueva")
    public ResponseEntity<?> save(@RequestBody StationDTO stationDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.saveNewStation(stationDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }

    @Operation(description = "Elimina parada mediante Id")
    @DeleteMapping("/estaciones/{id}")
    public ResponseEntity<?> deleteStation(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteStation(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }

    @Operation(description = "Suspender cuenta mediante Id")
    @PutMapping("/cuentas/{id}/suspender")
    public ResponseEntity<?> suspendAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.suspendAccount(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }

    @Operation(description = "Activar cuenta mediante Id")
    @PatchMapping("/cuentas/{id}/activar")
    public ResponseEntity<?> activateAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.activateAccount(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }

    @Operation(description = "Habilitar monopatin mediante Id")
    @PutMapping("/monopatin/{id}/habilitar")
    public ResponseEntity<?> enableScooter(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.enableScooter(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }

    @Operation(description = "Poner en mantenimiento monopatin mediante Id")
    @PutMapping("/monopatin/{id}/mantenimiento")
    public ResponseEntity<?> disableScooter(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.disableScooter(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }
}
