package com.example.microservice_account.controllers;

import com.example.microservice_account.DTOs.UserDTO;
import com.example.microservice_account.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(description = "Obtiene todos los usuarios")
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(description = "Crea un usuario")
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody UserDTO entity){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.save(entity));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo ingresar. " + e.getMessage());
        }
    }

    @Operation(description = "Obtiene un usuario por su id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Intente nuevamente. "+ e.getMessage());
        }
    }

    @Operation(description = "Elimina un usuario por su id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        try{
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente al usuario con id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo eliminar el usuario. " + e.getMessage());
        }
    }

    @Operation(description = "Actualiza un usuario por su id")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserDTO entity){
        try{
            userService.update(id, entity);
            return ResponseEntity.status(HttpStatus.OK).body("Se actualizo correctamente al usuario con id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar el usuario. " + e.getMessage());
        }
    }

    @Operation(description = "Vincula una cuenta a un usuario")
    @PutMapping("/{userId}/vincular/cuenta/{accountId}")
    public ResponseEntity<?> asociarCuenta(@PathVariable long userId, @PathVariable long accountId){
        try{
            userService.asociarCuenta(userId, accountId);
            return ResponseEntity.status(HttpStatus.OK).body("Se asocio correctamente la cuenta con accountId: " + accountId + " al usuario con userId: " + userId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo asociar la cuenta. " + e.getMessage());
        }
    }

    @Operation(description = "Desvincula una cuenta de un usuario")
    @DeleteMapping("/desvincular/usuario/{userId}/cuenta/{accountId}")
    public ResponseEntity<?> desvincularCuenta(@PathVariable long userId, @PathVariable long accountId){
        try{
            userService.desvincularCuenta(userId, accountId);
            return ResponseEntity.status(HttpStatus.OK).body("Se desvinculo correctamente la cuenta con accountId: " + accountId + " del usuario con userId: " + userId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo desvincular la cuenta. " + e.getMessage());
        }
    }

    @GetMapping("/{id}/cuentas")
    public ResponseEntity<?> getAccountsByUserId(@PathVariable long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getCuentasByUserId(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudieron obtener cuentas. " + e.getMessage());
        }
    }
}