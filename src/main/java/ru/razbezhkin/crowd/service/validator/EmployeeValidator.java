package ru.razbezhkin.crowd.service.validator;

import ru.razbezhkin.crowd.dto.EmployeeDto;

public interface EmployeeValidator {
    String validateAndError(EmployeeDto employeeDto);
}
