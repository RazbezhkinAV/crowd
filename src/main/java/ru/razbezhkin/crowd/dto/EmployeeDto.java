package ru.razbezhkin.crowd.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto implements Serializable {
    private String login;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
}
