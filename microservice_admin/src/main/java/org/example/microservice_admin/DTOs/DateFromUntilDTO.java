package org.example.microservice_admin.DTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class DateFromUntilDTO {
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
}
