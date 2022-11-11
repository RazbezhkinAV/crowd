package ru.razbezhkin.crowd.service.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.razbezhkin.crowd.domain.Employee;
import ru.razbezhkin.crowd.dto.request.EmployeeUpdateDto;
import ru.razbezhkin.crowd.dto.request.RequestEmployeeDto;
import ru.razbezhkin.crowd.repository.EmployeeRepository;
import ru.razbezhkin.crowd.utils.DynamicProperty;

@Component
@RequiredArgsConstructor
public class LoginEmployeeValidator implements EmployeeValidator {

    private final EmployeeRepository employeeRepository;

    @Override
    public String validateAndError(RequestEmployeeDto employeeDto) {
        return validate(employeeDto.getLogin());
    }

    @Override
    public String updateValidateAndError(EmployeeUpdateDto employeeUpdateDto, Employee employee) {
        DynamicProperty<String> newLogin = employeeUpdateDto.getLogin();

        if (newLogin == null ||
            !newLogin.isPresent() ||
            newLogin.getValue().equals(employee.getLogin()))
        {
            return "";
        }

        return validate(newLogin.getValue());
    }

    private String validate(String login) {
        if (login == null || login.isEmpty()) {
            return "Login cannot be empty";
        } else if (employeeRepository.existsByLogin(login)) {
            return "Login is busy :" + login;
        }

        return "";
    }
}
