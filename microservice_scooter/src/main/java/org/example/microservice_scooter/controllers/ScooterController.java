package org.example.microservice_scooter.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.microservice_scooter.dtos.ScooterDTO;
import org.example.microservice_scooter.services.ScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monopatines")
@AllArgsConstructor
public class ScooterController {

    @Autowired
    private ScooterService scooterService;


    //------------------------------------------------------------ PUNTO 3 C ------------------------------------------------------------
    @Operation(description = "Trae todos los monopatines con x viajes en j año")
    @GetMapping("/año/{year}/minimos-viajes/{minimTrips}")
    public ResponseEntity<?> getScootersByYear(@PathVariable int year, @PathVariable int minimTrips){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.findScootersByYear(year, minimTrips));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener monopatines " + e.getMessage());
        }
    }

    @Operation(description = "Obtiene todas los monopatines")
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener monopatines. " + e.getMessage());
        }
    }

    @Operation(description = "Agrega un monopatin")
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ScooterDTO scooterDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.save(scooterDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al agregar monopatin. " + e.getMessage());
        }
    }

    @Operation(description = "Obtiene monopatin por su id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al obtener monopatin. " + e.getMessage());
        }
    }

    @Operation(description = "Elimina monopatin por su id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        try{
            scooterService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente el monopatin con id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar monopatin. " + e.getMessage());
        }
    }

    @Operation(description = "Actualiza monopatin por su id")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody ScooterDTO scooterDTO) {
        try{
            scooterService.update(id, scooterDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Se actualizaron correctamente los datos del monopatin con id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar los datos. " + e.getMessage());
        }
    }

    @PutMapping("/{scooterId}/pausar/viaje/{tripId}/usuario/{userId}")
    public ResponseEntity<?> startPause(@PathVariable long scooterId,
                                        @PathVariable long tripId,
                                        @PathVariable long userId) {
        try {
            scooterService.startPause(scooterId, tripId, userId);
            return ResponseEntity.status(HttpStatus.OK).body("La pausa inicio para el monopatin con id: " + scooterId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al realizar la pausa. " + e.getMessage());
        }
    }

    @PutMapping("/{scooterId}/despausar/viaje/{tripId}/usuario/{userId}")
    public ResponseEntity<?> endPause(@PathVariable long scooterId,
                                      @PathVariable long tripId,
                                      @PathVariable long userId) {
        try {
            scooterService.endPause(scooterId, tripId, userId);
            return ResponseEntity.status(HttpStatus.OK).body("La pausa finalizo correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al frenar la pausa. " + e.getMessage());
        }
    }

    @GetMapping("/reporte/kilometros")
    public ResponseEntity<?> getReportByKilometers() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.findByKilometers());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener reporte. " + e.getMessage());
        }
    }

    @GetMapping("/reporte/tiempo-de-uso")
    public ResponseEntity<?> getReportByUseTime(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.findByUseTime());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener reporte. " + e.getMessage());
        }
    }

    @Operation(description = "Habilitar monopatin por Id")
    @PutMapping("/habilitar/{id}")
    public ResponseEntity<?> activate(@PathVariable long id){
        try{
            scooterService.enableScooter(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se habilito el monopatin con Id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al habilitar el monopatin" + e.getMessage());
        }
    }

    @Operation(description = "Poner en mantenimiento monopatin mediante Id")
    @PutMapping("/mantenimiento/{id}")
    public ResponseEntity<?> update(@PathVariable long id){
        try{
            scooterService.disableScooter(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se puso en mantenimiento el monopatin con Id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al poner en mantenimiento el monopatin" + e.getMessage());
        }
    }

}