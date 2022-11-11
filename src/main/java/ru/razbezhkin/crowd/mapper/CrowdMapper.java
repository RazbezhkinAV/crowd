package ru.razbezhkin.crowd.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.razbezhkin.crowd.domain.Employee;
import ru.razbezhkin.crowd.dto.EmployeeDto;
import ru.razbezhkin.crowd.dto.request.RequestEmployeeDto;

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

    public static RequestEmployeeDto toRequestEmployeeDto(EmployeeDto employeeDto) {
        return RequestEmployeeMapper.INSTANCE.toRequestEmployeeDto(employeeDto);
    }

    public static Employee toEmployee(EmployeeDto employeeDto) {
        return EmployeeMapper.INSTANCE.toEmployee(employeeDto);
    }

    public static Employee toEmployee(RequestEmployeeDto requestEmployeeDto) {
        return RequestEmployeeMapper.INSTANCE.toEmployee(requestEmployeeDto);
    }
}
