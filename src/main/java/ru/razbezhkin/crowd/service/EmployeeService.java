package ru.razbezhkin.crowd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.razbezhkin.crowd.domain.Employee;
import ru.razbezhkin.crowd.dto.EmployeeDto;
import ru.razbezhkin.crowd.exception.EntityNotFoundException;
import ru.razbezhkin.crowd.exception.ValidationException;
import ru.razbezhkin.crowd.mapper.CrowdMapper;
import ru.razbezhkin.crowd.repository.EmployeeRepository;
import ru.razbezhkin.crowd.service.validator.EmployeeValidator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final List<EmployeeValidator> validators;

    public List<EmployeeDto> getAllEmployee() {
        return CrowdMapper.toEmployeeDtoList(employeeRepository.findAll());
    }

    public List<EmployeeDto> getEmployeesByName(String name) {
        return CrowdMapper.toEmployeeDtoList(employeeRepository.findByName(name));
    }

    @Transactional
    public void saveEmployee(EmployeeDto employeeDto) {
        validateNew(employeeDto);
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
            .orElseThrow(() -> new EntityNotFoundException("Employee with " + login + " not found"));
    }


    private void validateNew(EmployeeDto employee) {
        List<String> errors = validators.stream()
            .map(it -> it.validateAndError(employee))
            .filter(it -> !it.isEmpty())
            .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            throw new ValidationException(
                errors.stream().collect(Collectors.joining(" ,", "Errors with create employee - [ ", " ]")));
        }
    }
}
