package com.example.microservice_account.DTOs;

import com.example.microservice_account.entities.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String surname;
    private long phoneNumber;
    private String email;;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
    }

    public UserDTO(long id, String name, String surname, long phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}