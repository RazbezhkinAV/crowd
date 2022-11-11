package ru.razbezhkin.crowd.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.razbezhkin.crowd.utils.DynamicProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class EmployeeUpdateDto {
    private DynamicProperty<String> login;
    private DynamicProperty<String> firstname;
    private DynamicProperty<String> lastname;
    private DynamicProperty<String> email;
    private DynamicProperty<String> phoneNumber;
}
