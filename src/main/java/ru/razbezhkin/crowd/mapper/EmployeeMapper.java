package ru.razbezhkin.crowd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.razbezhkin.crowd.domain.Employee;
import ru.razbezhkin.crowd.dto.EmployeeDto;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto toEmployeeDto(Employee employee);
    Employee toEmployee(EmployeeDto employeeDto);

}
