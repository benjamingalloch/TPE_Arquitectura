package org.example.microservice_admin.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.example.microservice_admin.DTOs.DateFromUntilDTO;
import org.example.microservice_admin.DTOs.NewScooterDTO;
import org.example.microservice_admin.DTOs.StationDTO;
import org.example.microservice_admin.Services.AdminService;
import org.example.microservice_admin.Services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/administracion")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BillService billService;


    //------------------------------------------------------------ PUNTO 3 B ------------------------------------------------------------
    @Operation(description = "Suspender cuenta mediante Id")
    @PutMapping("/cuenta/{id}/suspender")
    public ResponseEntity<?> suspendAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.suspendAccount(id));
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

    //------------------------------------------------------------ PUNTO 3 D ------------------------------------------------------------
    @Operation(description = "Obtiene total facturado en un rango fechas")
    @GetMapping("/facturacion/entre")
    public ResponseEntity<?> getBillingByTime(@RequestBody DateFromUntilDTO dateFromUntilDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(billService.getBillingByTime(dateFromUntilDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }

    //------------------------------------------------------------ PUNTO 3 E ------------------------------------------------------------
    @Operation(description = "Trae todos los monopatines por estado")
    @GetMapping("/monopatines/{status}")
    public ResponseEntity<?> getAllScootersByStatus(@PathVariable String status) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllScootersByStatus(status));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------


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

    @Operation(description = "Activar cuenta mediante Id")
    @PutMapping("/cuenta/{id}/habilitar")
    public ResponseEntity<?> activateAccount(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.activateAccount(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }

    @Operation(description = "Habilitar monopatin mediante Id")
    @PutMapping ("/monopatin/{id}/habilitar")
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
