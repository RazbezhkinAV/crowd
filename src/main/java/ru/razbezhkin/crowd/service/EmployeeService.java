package ru.razbezhkin.crowd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.razbezhkin.crowd.domain.Employee;
import ru.razbezhkin.crowd.dto.EmployeeDto;
import ru.razbezhkin.crowd.exception.EmployeeNotFoundException;
import ru.razbezhkin.crowd.mapper.CrowdMapper;
import ru.razbezhkin.crowd.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> getAllEmployee() {
        return CrowdMapper.toEmployeeDtoList(employeeRepository.findAll());
    }

    public List<EmployeeDto> getEmployeesByName(String name) {
        return CrowdMapper.toEmployeeDtoList(employeeRepository.findByName(name));
    }

    public void saveEmployee(EmployeeDto employeeDto) {
        employeeRepository.save(CrowdMapper.toEmployee(employeeDto));
    }

    public void deleteEmployee(EmployeeDto employeeDto) {
        employeeRepository.delete(findEmployeeByLogin(employeeDto.getLogin()));
    }

    public EmployeeDto getEmployeeByLogin(String login) {
        return CrowdMapper.toEmployeeDto(findEmployeeByLogin(login));
    }

    private Employee findEmployeeByLogin(String login) {
        return employeeRepository.findEmployeeByLogin(login)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee with " + login + " not found"));
    }
}
