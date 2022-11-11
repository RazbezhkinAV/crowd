package ru.razbezhkin.crowd.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestEmployeeDto {
    private String login;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
}
