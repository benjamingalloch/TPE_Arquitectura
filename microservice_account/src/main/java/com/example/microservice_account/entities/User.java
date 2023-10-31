package com.example.microservice_account.entities;

import com.example.microservice_account.DTOs.UserDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class User {
    @Column(name="name")
    private String name;
    @Column(name="surname")
    private String surname;
    @Column(name="phone_number")
    private long phoneNumber;
    @Column(name="email")
    private String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserAccount> accounts;

    public User(){
        super();
        this.accounts = new HashSet<>();
    }

    public User(String name, String surname, long phoneNumber, String email) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.accounts = new HashSet<>();
    }

    public User(UserDTO dto){
        this.name = dto.getName();
        this.surname = dto.getSurname();
        this.phoneNumber = dto.getPhoneNumber();
        this.email = dto.getEmail();
        this.accounts = new HashSet<>();
    }
}
