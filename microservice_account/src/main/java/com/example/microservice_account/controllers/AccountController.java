package com.example.microservice_account.controllers;

import com.example.microservice_account.DTOs.AccountDTO;
import com.example.microservice_account.services.AccountService;

import com.example.microservice_account.services.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserAccountService userAccountService;

    @Operation(summary = "Obtiene todos las cuentas.", description = "Obtiene todas las cuentas")
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(accountService.getAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @GetMapping("/accounts/users/{userId}")
    public ResponseEntity<?> getCuentasByUserId(@PathVariable long userId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userAccountService.getCuentasByUserId(userId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Crea una nueva cuenta.", description = "Crea una cuenta")
    @PostMapping("/register")
    public ResponseEntity<?> save(@RequestBody AccountDTO entity){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(accountService.save(entity));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Obtiene una cuenta por su id.", description = "Obtiene una cuenta por su accountId")
    @GetMapping("/search/{accountId}")
    public ResponseEntity<?> getById(@PathVariable long accountId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(accountService.findById(accountId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Elimina una cuenta por su id.", description = "Elimina una cuenta por su accountId")
    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<?> delete(@PathVariable long accountId){
        try{
            accountService.delete(accountId);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente la cuenta con accountId: " + accountId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo eliminar el account, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Desactiva una cuenta por su id.", description = "Desactiva una cuenta por su accountId")
    @PutMapping("/suspend/{accountId}")
    public ResponseEntity<?> suspend(@PathVariable long accountId){
        try{
            accountService.suspendAccount(accountId);
            return ResponseEntity.status(HttpStatus.OK).body("Se suspendio correctamente la cuenta con accountId: " + accountId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo suspender la cuenta, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Activa una cuenta por su id.", description = "Activa una cuenta por su accountId")
    @PutMapping("/activate/{accountId}")
    public ResponseEntity<?> activate(@PathVariable long accountId){
        try{
            accountService.activateAccount(accountId);
            return ResponseEntity.status(HttpStatus.OK).body("Se activo correctamente la cuenta con accountId: " + accountId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo activar la cuenta, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Actualiza una cuenta por su id.", description = "Actualiza una cuenta por su accountId")
    @PutMapping("/update/{accountId}")
    public ResponseEntity<?> update(@PathVariable long accountId, @RequestBody AccountDTO entity){
        try{
            accountService.update(accountId, entity);
            return ResponseEntity.status(HttpStatus.OK).body("Se actualizo correctamente la cuenta con accountId: " + accountId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo actualizar la cuenta, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Vincula una cuenta a un usuario.", description = "Vincula un usuario a una cuenta")
    @PutMapping("/link/user/{userId}/account/{accountId}")
    public ResponseEntity<?> asociarUsuario(@PathVariable long userId, @PathVariable long accountId){
        try{
            accountService.linkUser(userId, accountId);
            return ResponseEntity.status(HttpStatus.OK).body("Se asocio correctamente el usuario con userId: " + userId + " a la cuenta con accountId: " + accountId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo asociar el usuario a la cuenta, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Desvincula una cuenta de un usuario.", description = "Desvincula un usuario de una cuenta")
    @DeleteMapping("/unlink/user/{userId}/account/{accountId}")
    public ResponseEntity<?> desvincularUsuario(@PathVariable long userId, @PathVariable long accountId){
        try{
            accountService.unlinkUser(userId, accountId);
            return ResponseEntity.status(HttpStatus.OK).body("Se desvinculo correctamente el usuario con userId: " + userId + " de la cuenta con accountId: " + accountId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo desvincular el usuario de la cuenta, revise los campos e intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Obtiene el balance de una cuenta.", description = "Obtiene el balance de una cuenta")
    @GetMapping("/balance/get/{accountId}")
    public ResponseEntity<?> getSaldo(@PathVariable long accountId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(accountService.getBalance(accountId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Actualiza el balance de una cuenta.", description = "Actualiza el balance de una cuenta")
    @PutMapping("/balance/update/{accountId}")
    public void updateSaldo(@PathVariable long accountId, @RequestBody Double balance) {
        accountService.updateBalance(balance, accountId);
    }
}