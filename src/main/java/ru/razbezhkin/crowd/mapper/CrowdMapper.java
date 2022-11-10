package ru.razbezhkin.crowd.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.razbezhkin.crowd.domain.Employee;
import ru.razbezhkin.crowd.dto.EmployeeDto;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CrowdMapper {

    public static List<EmployeeDto> toEmployeeDtoList(List<Employee> employees) {
        return employees.stream()
                .map(CrowdMapper::toEmployeeDto)
                .collect(Collectors.toList());
    }

    public static EmployeeDto toEmployeeDto(Employee employee) {
        return EmployeeMapper.INSTANCE.toEmployeeDto(employee);
    }

    public static Employee toEmployee(EmployeeDto employeeDto) {
        return EmployeeMapper.INSTANCE.toEmployee(employeeDto);
    }
}
