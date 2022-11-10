package ru.razbezhkin.crowd.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private String login;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
}
