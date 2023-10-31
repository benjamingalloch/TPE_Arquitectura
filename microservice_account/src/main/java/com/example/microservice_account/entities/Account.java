package com.example.microservice_account.entities;


import com.example.microservice_account.DTOs.AccountDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;



import java.security.Timestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private long accountId;
    @Column(name="register_date")
    private LocalDate registerDate;
    @Column(name = "available")
    private boolean available;
    @Column(name = "id_mpago")
    private String idMPago;
    @Column(name = "balance", nullable = false)
    private Double balance;


    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Set<UserAccount> users;

    public Account() {
        super();
        this.users = new HashSet<>();
    }

    public Account(boolean available, String idMPago, Double balance) {
        super();
        this.registerDate = LocalDate.now();
        this.available = available;
        this.idMPago = idMPago;
        this.balance = balance;
        this.users = new HashSet<>();
    }

    public Account(AccountDTO entity) {
        this.registerDate = LocalDate.now();
        this.available = entity.isAvailable();
    }
}
