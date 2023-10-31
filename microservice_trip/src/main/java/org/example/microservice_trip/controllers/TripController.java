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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }
    @Operation(description = "Registrar un viaje cuando comienza")
    @PostMapping("/usuario/{userId}/monopatin/{scooterId}")
    public ResponseEntity<?> save(@PathVariable long userId, @PathVariable long scooterId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tripService.save(userId, scooterId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(description = "Obtiene un viaje por id")
    @GetMapping("/{tripId}")
    public ResponseEntity<?> getById(@PathVariable long tripId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tripService.findById(tripId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Eliminia un viaje por su id.", description = "Elimina un viaje por su travelId")
    @DeleteMapping("/{tripId}")
    public ResponseEntity<?> delete(@PathVariable long tripId){
        try{
            tripService.delete(tripId);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente el viaje con travelId: " + tripId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo eliminar la estacion, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(description = "Actualiza los datos de un viaje por su id")
    @PutMapping("/actualizar/{tripId}")
    public ResponseEntity<?> update(@PathVariable long tripId, @RequestBody TripDTO entity){
        try{
            tripService.update(tripId, entity);
            return ResponseEntity.status(HttpStatus.OK).body("Se actualizaron correctamente los datos del viaje con id: " + tripId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudieron actualizar los datos de la estacion, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Finaliza un viaje por su id.", description = "Finaliza un viaje por su travelId")
    @PutMapping("/finalizar/{tripId}")
    public ResponseEntity<?> travelEnd(@PathVariable long tripId){
        try{
            tripService.tripEnd(tripId);
            return ResponseEntity.status(HttpStatus.OK).body("Se finalizo correctamente el viaje con travelId: " + tripId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo finalizar el viaje, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

}
