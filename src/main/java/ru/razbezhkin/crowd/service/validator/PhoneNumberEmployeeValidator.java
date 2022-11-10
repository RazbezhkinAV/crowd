package ru.razbezhkin.crowd.service.validator;

import org.springframework.stereotype.Component;
import ru.razbezhkin.crowd.dto.EmployeeDto;

import java.util.regex.Pattern;

@Component
public class PhoneNumberEmployeeValidator implements EmployeeValidator{

    private static final String PATTERN = "^\\+?7(9\\d{9})$";

    @Override
    public String validateAndError(EmployeeDto employeeDto) {

        String phoneNumber = employeeDto.getPhoneNumber();

        if (phoneNumber == null) {
            return "Phone Number cannot be null";
        }else if(!Pattern.matches(PATTERN,phoneNumber)){
            return "Phone number must be like +7 / 7 (9xx) xxx xx x, your number: " + phoneNumber;
        }
        return "";
    }
}
