package org.example.microservice_trip.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String surname;
    private long phoneNumber;
    private String email;

    public UserDTO(long id, String name, String surname, long phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}