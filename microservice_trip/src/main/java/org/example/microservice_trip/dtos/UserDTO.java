package org.example.microservice_trip.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDTO {
    private long userId;
    private String name;
    private String surname;
    private long phoneNumber;
    private String email;

    public UserDTO(long userId, String name, String surname, long phoneNumber, String email) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}