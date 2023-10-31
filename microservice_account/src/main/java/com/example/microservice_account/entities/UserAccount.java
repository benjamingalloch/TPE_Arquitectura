package com.example.microservice_account.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name = "user_account")
@Data
@IdClass(UserAccountPK.class)
public class UserAccount {
    @Id
    private User user;

    @Id
    private Account account;

    public UserAccount(){
        super();
    }

    public UserAccount(User user, Account account) {
        this.user = Objects.requireNonNull(user, "user must not be null");
        this.account = Objects.requireNonNull(account, "account must not be null");
    }
}
