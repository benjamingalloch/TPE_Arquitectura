package org.example.microservice_station.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.example.microservice_station.dtos.StationDTO;
import org.example.microservice_station.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estaciones")
public class StationController {

    @Autowired
    private StationService stationService;

    @Operation(description = "Obtiene todas las estaciones")
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(stationService.findAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }
    @Operation(description = "Agrega estacion")
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody StationDTO entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(stationService.save(entity));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo ingresar, revise los campos e intente nuevamente " + e.getMessage());
        }
    }

    @Operation(description = "Obtiene estacion por Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(stationService.findById(id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error " + e.getMessage());
        }
    }

    @Operation(description = "Elimina estacion por Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            stationService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente la estacion con stationId: " + id);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo eliminar la estacion, revise los campos e intente nuevamente " + e.getMessage());
        }
    }

    @Operation(description = "Actualiza estacion por Id")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody StationDTO entity) {
        try {
            stationService.update(id, entity);
            return ResponseEntity.status(HttpStatus.OK).body("Se actualizaron correctamente los datos de la estacion con stationId: " + id);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudieron actualizar los datos de la estacion, revise los campos e intente nuevamente " + e.getMessage());
        }
    }

    @Operation(description = "Agrega monopatin a estacion mediante Id")
    @PostMapping("/{stationId}/monopatin/{scooterId}")
    public ResponseEntity<?> addScooter(@PathVariable Long stationId, @PathVariable Long scooterId) {
        try {
            stationService.addScooter(stationId, scooterId);
            return ResponseEntity.status(HttpStatus.OK).body("Se agrego el monopatin " + scooterId + " a la estacion " + stationId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo agregar el monopatin. Revise los campos. Error: " + e.getMessage());
        }
    }

    @Operation(description = "Quitar monopatin de estacion mediante Id")
    @DeleteMapping ("/{stationId}/monopatin/{scooterId}")
    public ResponseEntity<?> removeScooter(@PathVariable Long stationId, @PathVariable Long scooterId) {
        try {
            stationService.removeScooter(stationId, scooterId);
            return ResponseEntity.status(HttpStatus.OK).body("Se quito el monopatin " + scooterId + " a la estacion " + stationId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo quitar el monopatin. Revise los campos. Error: " + e.getMessage());
        }
    }
}
