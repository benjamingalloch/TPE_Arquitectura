package org.example.microservice_admin.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.example.microservice_admin.DTOs.NewBillDTO;
import org.example.microservice_admin.Services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administration")
public class BillController {

    @Autowired
    private BillService billService;

    @Operation(summary = "Obtiene todos los integrantes del staff de administracion.", description = "Obtiene todos los integrantes del staff de administracion.")
    @GetMapping("/billing")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(billService.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Obtiene una factura por id.", description = "Obtiene una factura por id.")
    @GetMapping("/billing/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(billService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Carga una nueva factura.", description = "Carga una nueva factura.")
    @PostMapping("/billing/nueva")
    public ResponseEntity<?> save(@RequestBody NewBillDTO NewBillDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(billService.save(NewBillDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

    @Operation(summary = "Obtener facturacion en un rango de fechas.", description = "Obtener facturacion en un rango de fechas.")
    @GetMapping("/billing/dateSince/{fechaDesde}/dateTo/{fechaHasta}")
    public ResponseEntity<?> getFacturacion(@PathVariable String fechaDesde, @PathVariable String fechaHasta) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(billService.getBilling(fechaDesde, fechaHasta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Intente nuevamente.\"\n\"error\":\"" + e.getMessage()+"\"}");
        }
    }

}