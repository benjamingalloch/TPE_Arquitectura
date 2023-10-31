package org.example.microservice_trip.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.example.microservice_trip.dtos.TripDTO;
import org.example.microservice_trip.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @Operation(summary = "Obtiene todos los viajes.", description = "Obtiene todos los viajes")
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tripService.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Agrega un viaje.", description = "Agrega un viaje")
    @PostMapping("/alta/usuario/{idUsuario}/scooter/{idScooter}")
    public ResponseEntity<?> save(@RequestBody long idUsuario, long idScooter) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tripService.save(idUsuario, idScooter));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Obtiene una estacion viaje por su id.", description = "Obtiene un viaje por su travelId")
    @GetMapping("/buscar/{travelId}")
    public ResponseEntity<?> getById(@PathVariable long travelId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tripService.findById(travelId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Eliminia un viaje por su id.", description = "Elimina un viaje por su travelId")
    @DeleteMapping("/eliminar/{travelId}")
    public ResponseEntity<?> delete(@PathVariable long travelId){
        try{
            tripService.delete(travelId);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente el viaje con travelId: " + travelId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo eliminar la estacion, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Actualiza los datos de un viaje por su id.", description = "Actualiza los datos de un viaje por su travelId")
    @PutMapping("/actualizar/{travelId}")
    public ResponseEntity<?> update(@PathVariable long travelId, @RequestBody TripDTO entity){
        try{
            tripService.update(travelId, entity);
            return ResponseEntity.status(HttpStatus.OK).body("Se actualizaron correctamente los datos del viaje con travelId: " + travelId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudieron actualizar los datos de la estacion, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Finaliza un viaje por su id.", description = "Finaliza un viaje por su travelId")
    @PutMapping("/finalizar/{travelId}")
    public ResponseEntity<?> travelEnd(@PathVariable long travelId){
        try{
            tripService.tripEnd(travelId);
            return ResponseEntity.status(HttpStatus.OK).body("Se finalizo correctamente el viaje con travelId: " + travelId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo finalizar el viaje, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

}
