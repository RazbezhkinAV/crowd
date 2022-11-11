package ru.razbezhkin.crowd.service.validator;

import ru.razbezhkin.crowd.domain.Employee;
import ru.razbezhkin.crowd.dto.request.EmployeeUpdateDto;
import ru.razbezhkin.crowd.dto.request.RequestEmployeeDto;

public interface EmployeeValidator {
    String validateAndError(RequestEmployeeDto employeeDto);

    String updateValidateAndError(EmployeeUpdateDto employeeUpdateDto, Employee employee);
}
