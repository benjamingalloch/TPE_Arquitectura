package com.example.microservice_account.controllers;

import com.example.microservice_account.DTOs.AccountDTO;
import com.example.microservice_account.services.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cuentas")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Operation(description = "Obtiene todas las cuentas")
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(accountService.getAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Intente nuevamente " + e.getMessage());
        }
    }

    @Operation(description = "Crea una cuenta")
    @PostMapping("/register")
    public ResponseEntity<?> save(@RequestBody AccountDTO entity){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(accountService.save(entity));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo ingresar. " + e.getMessage());
        }
    }

    @Operation(description = "Obtiene una cuenta por su id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(accountService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Intente nuevamente " + e.getMessage());
        }
    }

    @Operation(description = "Elimina una cuenta por su id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        try{
            accountService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente la cuenta con id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo eliminar la cuenta. " + e.getMessage());
        }
    }

    @Operation(description = "Desactiva una cuenta por su id")
    @PutMapping("/suspend/{id}")
    public ResponseEntity<?> suspend(@PathVariable long id){
        try{
            accountService.suspendAccount(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se suspendio correctamente la cuenta con id : " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo suspender la cuenta. " + e.getMessage());
        }
    }

    @Operation(description = "Activa una cuenta por su id")
    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activate(@PathVariable long id){
        try{
            accountService.activateAccount(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se activo correctamente la cuenta con id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo activar la cuenta. " + e.getMessage());
        }
    }

    @Operation(description = "Actualiza una cuenta por su id")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody AccountDTO entity){
        try{
            accountService.update(id, entity);
            return ResponseEntity.status(HttpStatus.OK).body("Se actualizo correctamente la cuenta con accountId: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar la cuenta. " + e.getMessage());
        }
    }

    @Operation(description = "Vincula un usuario a una cuenta")
    @PutMapping("/{accountId}/vincular/usuario/{userId}")
    public ResponseEntity<?> asociarUsuario(@PathVariable long accountId, @PathVariable long userId){
        try{
            accountService.linkUser(userId, accountId);
            return ResponseEntity.status(HttpStatus.OK).body("Se asocio correctamente el usuario con userId: " + userId + " a la cuenta con accountId: " + accountId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo asociar el usuario a la cuenta. " + e.getMessage());
        }
    }

    @Operation(description = "Desvincula un usuario de una cuenta")
    @DeleteMapping("/unlink/user/{userId}/account/{accountId}")
    public ResponseEntity<?> desvincularUsuario(@PathVariable long userId, @PathVariable long accountId){
        try{
            accountService.unlinkUser(userId, accountId);
            return ResponseEntity.status(HttpStatus.OK).body("Se desvinculo correctamente el usuario con userId: " + userId + " de la cuenta con accountId: " + accountId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo desvincular el usuario de la cuenta. "+ e.getMessage());
        }
    }

    @Operation(description = "Obtiene el balance de una cuenta")
    @GetMapping("/balance/get/{id}")
    public ResponseEntity<?> getSaldo(@PathVariable long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(accountService.getBalance(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Intente nuevamente " + e.getMessage());
        }
    }

    @Operation(description = "Actualiza el balance de una cuenta")
    @PutMapping("/balance/update/{id}")
    public void updateSaldo(@PathVariable long id, @RequestBody Double balance) {
        accountService.updateBalance(balance, id);
    }
}