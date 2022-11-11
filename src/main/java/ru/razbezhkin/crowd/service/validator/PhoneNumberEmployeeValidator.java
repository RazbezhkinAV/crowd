package ru.razbezhkin.crowd.service.validator;

import org.springframework.stereotype.Component;
import ru.razbezhkin.crowd.domain.Employee;
import ru.razbezhkin.crowd.dto.request.EmployeeUpdateDto;
import ru.razbezhkin.crowd.dto.request.RequestEmployeeDto;
import ru.razbezhkin.crowd.utils.DynamicProperty;

import java.util.regex.Pattern;

@Component
public class PhoneNumberEmployeeValidator implements EmployeeValidator {

    private static final String PATTERN = "^\\+?7(9\\d{9})$";

    @Override
    public String validateAndError(RequestEmployeeDto employeeDto) {
        return validate(employeeDto.getPhoneNumber());
    }

    @Override
    public String updateValidateAndError(EmployeeUpdateDto employeeUpdateDto, Employee employee) {
        DynamicProperty<String> newPhoneNumber = employeeUpdateDto.getPhoneNumber();
        if (newPhoneNumber == null ||
            !newPhoneNumber.isPresent() ||
            newPhoneNumber.getValue().equals(employee.getPhoneNumber()))
        {
            return "";
        }

        return validate(newPhoneNumber.getValue());
    }

    private String validate(String phoneNumber) {
        if (phoneNumber == null) {
            return "Phone Number cannot be null";
        } else if (!Pattern.matches(PATTERN, phoneNumber)) {
            return "Phone number must be like +7 / 7 (9xx) xxx xx x, your number: " + phoneNumber;
        }
        return "";
    }
}
