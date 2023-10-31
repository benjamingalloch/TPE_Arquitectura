package org.example.microservice_admin.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.microservice_admin.DTOs.BillDTO;
import org.example.microservice_admin.DTOs.NewBillDTO;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="bill_date")
    private Timestamp billDate;
    @Column(name="amount")
    private Double amount;
    @Column(name="description")
    private String description;

    public Bill(){
        super();
    }

    public Bill(Timestamp billDate, Double amount, String description) {
        this.billDate = billDate;
        this.amount = amount;
        this.description = description;
    }

    public Bill(BillDTO dto){
        this.billDate = dto.getBillDate();
        this.amount = dto.getAmount();
        this.description = dto.getDescription();
    }

    public Bill(NewBillDTO dto){
        this.billDate = dto.getBillDate();
        this.amount = dto.getAmount();
        this.description = dto.getDescription();
    }
}