package com.example.microservice_account.DTOs;

import com.example.microservice_account.entities.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDTO {
    private long userId;
    private String name;
    private String surname;
    private long phoneNumber;
    private String email;;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
    }

    public UserDTO(long userId, String name, String surname, long phoneNumber, String email) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}