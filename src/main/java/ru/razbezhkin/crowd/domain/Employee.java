package ru.razbezhkin.crowd.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @SequenceGenerator(name = "employee_seq", sequenceName = "EMPLOYEE_SEQ", initialValue = 4, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "employee_seq")
    private Long id;
    private String login;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
}
