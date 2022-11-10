package ru.razbezhkin.crowd.service.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.razbezhkin.crowd.dto.EmployeeDto;
import ru.razbezhkin.crowd.repository.EmployeeRepository;

@Component
@RequiredArgsConstructor
public class LoginEmployeeValidator implements EmployeeValidator {

    private final EmployeeRepository employeeRepository;

    @Override
    public String validateAndError(EmployeeDto employeeDto) {
        String login = employeeDto.getLogin();

        if (login == null || login.isEmpty()) {
            return "Login cannot be empty";
        } else if (employeeRepository.existsByLogin(login)) {
            return "Login is busy :" + login;
        }

        return "";
    }
}
