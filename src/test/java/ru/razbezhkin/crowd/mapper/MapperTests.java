package ru.razbezhkin.crowd.mapper;

import org.junit.jupiter.api.Test;
import ru.razbezhkin.crowd.domain.Employee;
import ru.razbezhkin.crowd.dto.EmployeeDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTests {

    @Test
    void testEmployeeMapper() {
        Employee employee = new Employee(
                1L,
                "a.petrov",
                "Alex",
                "Petrov",
                "petrov@mail.ru",
                "79996999887");

        EmployeeDto expectedEmployeeDto = EmployeeDto.builder()
                .login("a.petrov")
                .firstname("Alex")
                .lastname("Petrov")
                .email("petrov@mail.ru")
                .phoneNumber("79996999887")
                .build();
        EmployeeDto actualEmployeeDto = EmployeeMapper.INSTANCE.toEmployeeDto(employee);

        assertEquals(actualEmployeeDto.getLogin(), expectedEmployeeDto.getLogin());
        assertEquals(actualEmployeeDto.getFirstname(), expectedEmployeeDto.getFirstname());
        assertEquals(actualEmployeeDto.getLastname(), expectedEmployeeDto.getLastname());
        assertEquals(actualEmployeeDto.getEmail(), expectedEmployeeDto.getEmail());
        assertEquals(actualEmployeeDto.getPhoneNumber(), expectedEmployeeDto.getPhoneNumber());

    }

}
