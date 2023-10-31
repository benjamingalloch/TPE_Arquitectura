package org.example.microservice_admin.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.microservice_admin.DTOs.WorkerDTO;

@Entity
@Data
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "role")
    private String role;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone_number")
    private long phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public Worker(){
        super();
    }

    public Worker(String role, String name, String surname, long phoneNumber, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public Worker(WorkerDTO dto){
        this.name = dto.getName();
        this.surname = dto.getSurname();
        this.phoneNumber = dto.getPhoneNumber();
        this.email = dto.getEmail();
        this.role = dto.getRole();
        this.password = dto.getPassword();
    }
}
