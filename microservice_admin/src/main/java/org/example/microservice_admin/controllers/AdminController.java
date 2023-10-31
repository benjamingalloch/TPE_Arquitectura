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

    @GetMapping("/monopatines")
    public ResponseEntity<?> getAllScooters() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.findAllScooters());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @PostMapping("/monopatines")
    public ResponseEntity<?> save(@RequestBody NewScooterDTO scooterDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.saveNewScooter(scooterDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @DeleteMapping("/monopatines/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteScooter(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @GetMapping("informes/reporteDeMonopatinesPor/KilometrosRecorridos")
    public ResponseEntity<?> getKilometros() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.getReportScootersByKms());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @PostMapping("paradas/nueva")
    public ResponseEntity<?> save(@RequestBody StationDTO stationDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.saveNewStation(stationDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @DeleteMapping("paradas/{id}")
    public ResponseEntity<?> deleteStation(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteStation(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @PutMapping("cuentas/suspender/{id}")
    public ResponseEntity<?> suspendAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.suspendAccount(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @PatchMapping("cuentas/activar/{id}")
    public ResponseEntity<?> activateAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.activateAccount(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }
}
