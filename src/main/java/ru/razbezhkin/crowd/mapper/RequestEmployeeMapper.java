package ru.razbezhkin.crowd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.razbezhkin.crowd.domain.Employee;
import ru.razbezhkin.crowd.dto.EmployeeDto;
import ru.razbezhkin.crowd.dto.request.RequestEmployeeDto;

@Mapper
public interface RequestEmployeeMapper {
    RequestEmployeeMapper INSTANCE = Mappers.getMapper(RequestEmployeeMapper.class);

    Employee toEmployee(RequestEmployeeDto requestEmployeeDto);

    RequestEmployeeDto toRequestEmployeeDto(EmployeeDto employeeDto);
}
