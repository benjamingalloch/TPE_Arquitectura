package org.example.microservice_admin.DTOs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.microservice_admin.Entities.Bill;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public class BillDTO {
    private long id;
    private Timestamp billDate;
    private Double amount;
    private String description;

    public BillDTO(long billId, Timestamp billDate, Double amount, String description) {
        this.billDate = billDate;
        this.amount = amount;
        this.description = description;
        this.id = billId;
    }

    public BillDTO(Bill bill) {
        this.id = bill.getId();
        this.billDate = bill.getBillDate();
        this.amount = bill.getAmount();
        this.description = bill.getDescription();
    }
}