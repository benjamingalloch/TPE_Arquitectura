package org.example.microservice_trip.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.example.microservice_trip.dtos.TripDTO;
import org.example.microservice_trip.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viajes")
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tripService.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener todos los viajes. " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tripService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al obtener viaje. " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        try{
            tripService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente el viaje con travelId: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo eliminar el viaje. " + e.getMessage());
        }
    }

    @Operation(description = "Registrar un viaje cuando comienza")
    @PostMapping("/usuario/{userId}/monopatin/{scooterId}")
    public ResponseEntity<?> startTrip(@PathVariable long userId, @PathVariable long scooterId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tripService.startTrip(userId, scooterId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo registrar el viaje. " + e.getMessage());
        }
    }


    @Operation(description = "Actualiza los datos de un viaje por su id")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody TripDTO tripDTO){
        try{
            tripService.update(id, tripDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Se actualizaron correctamente los datos del viaje con id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudieron actualizar los datos del viaje. " + e.getMessage());
        }
    }

    @Operation(description = "Finaliza un viaje por su id")
    @PutMapping("/finalizar/{id}")
    public ResponseEntity<?> endTrip(@PathVariable long id){
        try{
            tripService.tripEnd(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se finalizo correctamente el viaje con travelId: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo finalizar el viaje. " + e.getMessage());
        }
    }

}
