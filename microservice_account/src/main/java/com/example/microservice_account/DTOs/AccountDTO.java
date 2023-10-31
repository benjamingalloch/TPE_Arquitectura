package com.example.microservice_account.DTOs;

import com.example.microservice_account.entities.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class AccountDTO {
    private LocalDate registerDate;
    private boolean available;
    private String idMPago;
    private Double balance;

    public AccountDTO(Account account) {
        this.registerDate = account.getRegisterDate();
        this.available = account.isAvailable();
        this.balance = account.getBalance();
        this.idMPago = account.getIdMPago();
    }

    public AccountDTO(LocalDate registerDate, boolean available, String idMPago, Double balance) {
        this.registerDate = registerDate;
        this.available = available;
        this.idMPago = idMPago;
        this.balance = balance;
    }
}
