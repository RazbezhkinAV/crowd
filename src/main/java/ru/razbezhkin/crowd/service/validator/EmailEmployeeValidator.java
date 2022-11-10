package ru.razbezhkin.crowd.service.validator;

import org.springframework.stereotype.Component;
import ru.razbezhkin.crowd.dto.EmployeeDto;

import java.util.regex.Pattern;

@Component
public class EmailEmployeeValidator implements EmployeeValidator {

    private static final String PATTERN = "^" + "([a-zA-Z0-9_\\.\\-+])+" // local
        + "@" + "[a-zA-Z0-9-.]+" // domain
        + "\\." + "[a-zA-Z0-9-]{2,}" // tld
        + "$";

    @Override
    public String validateAndError(EmployeeDto employeeDto) {
        String email = employeeDto.getEmail();

        if (email == null) {
            return "Email cannot be null";
        }else if(!Pattern.matches(PATTERN, email)){
            return "Invalid email :" + email;
        }
        return "";
    }
}
