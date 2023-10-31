package org.example.microservice_trip.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AccountDTO {
    private long accountId;
    private Timestamp registerDate;
    private boolean available;
    private String idMPago;
    private Double balance;

    public AccountDTO(long accountId, Timestamp registerDate, boolean available, String idMPago, Double balance) {
        this.accountId = accountId;
        this.registerDate = registerDate;
        this.available = available;
        this.balance = balance;
        this.idMPago = idMPago;
    }


}