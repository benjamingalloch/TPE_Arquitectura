package org.example.microservice_admin.DTOs;

import org.example.microservice_admin.Entities.Worker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorkerDTO {
    private long adminId;
    private String name;
    private String surname;
    private long phoneNumber;
    private String email;
    private String role;
    private String password;

    public WorkerDTO(Worker admin) {
        this.adminId = admin.getId();
        this.name = admin.getName();
        this.surname = admin.getSurname();
        this.phoneNumber = admin.getPhoneNumber();
        this.email = admin.getEmail();
        this.role = admin.getRole();
        this.password = admin.getPassword();

    }

    public WorkerDTO(String role, long adminId, String name, String surname, long phoneNumber, String email, String password) {
        this.adminId = adminId;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.password = password;
    }


    public WorkerDTO(WorkerDTO dto) {
        this.name = dto.getName();
        this.surname = dto.getSurname();
        this.phoneNumber = dto.getPhoneNumber();
        this.email = dto.getEmail();
        this.role = dto.getRole();
        this.password = dto.getPassword();
    }
}