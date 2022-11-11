package ru.razbezhkin.crowd.service.validator;

import org.springframework.stereotype.Component;
import ru.razbezhkin.crowd.domain.Employee;
import ru.razbezhkin.crowd.dto.request.EmployeeUpdateDto;
import ru.razbezhkin.crowd.dto.request.RequestEmployeeDto;
import ru.razbezhkin.crowd.utils.DynamicProperty;

import java.util.regex.Pattern;

@Component
public class EmailEmployeeValidator implements EmployeeValidator {

    private static final String PATTERN = "^" + "([a-zA-Z0-9_\\.\\-+])+" // local
        + "@" + "[a-zA-Z0-9-.]+" // domain
        + "\\." + "[a-zA-Z0-9-]{2,}" // tld
        + "$";

    @Override
    public String validateAndError(RequestEmployeeDto employeeDto) {
        return validate(employeeDto.getEmail());
    }

    @Override
    public String updateValidateAndError(EmployeeUpdateDto employeeUpdateDto, Employee employee) {
        DynamicProperty<String> newEmail = employeeUpdateDto.getEmail();

        if (newEmail == null ||
            !newEmail.isPresent() ||
            newEmail.getValue().equals(employee.getEmail()))
        {
            return "";
        }

        return validate(newEmail.getValue());
    }

    private String validate(String email) {
        if (email == null) {
            return "Email cannot be null";
        } else if (!Pattern.matches(PATTERN, email)) {
            return "Invalid email :" + email;
        }
        return "";
    }
}
