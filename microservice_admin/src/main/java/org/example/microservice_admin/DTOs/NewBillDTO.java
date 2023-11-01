package org.example.microservice_admin.DTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.microservice_admin.Entities.Bill;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class NewBillDTO {
    private LocalDateTime billDate;
    private Double amount;
    private String description;

    public NewBillDTO(LocalDateTime  billDate, Double amount, String description) {
        this.billDate = billDate;
        this.amount = amount;
        this.description = description;
    }

    public NewBillDTO(Bill bill) {
        this.billDate = bill.getBillDate();
        this.amount = bill.getAmount();
        this.description = bill.getDescription();
    }
}
