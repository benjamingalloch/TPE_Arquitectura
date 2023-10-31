package com.example.microservice_account.entities;


import com.example.microservice_account.DTOs.AccountDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;



import java.security.Timestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="register_date")
    private LocalDate registerDate;
    @Column(name = "available")
    private boolean available;
    @Column(name = "id_mpago")
    private String idMPago;
    @Column(name = "balance", nullable = false)
    private Double balance;


    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany
    @JoinTable(name = "user_account",  // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public Account() {
        super();
    }

    public Account(boolean available, String idMPago, Double balance) {
        super();
        this.registerDate = LocalDate.now();
        this.available = available;
        this.idMPago = idMPago;
        this.balance = balance;
    }

    public Account(AccountDTO entity) {
        this.registerDate = LocalDate.now();
        this.available = entity.isAvailable();
    }

    public void addUser(User user) {
        if (!this.users.contains(user)) {
            this.users.add(user);
        }
    }

    public void deleteUser(User user){
        this.users.remove(user);
    }
}
