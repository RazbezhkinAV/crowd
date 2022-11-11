package ru.razbezhkin.crowd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.razbezhkin.crowd.domain.Employee;
import ru.razbezhkin.crowd.dto.EmployeeDto;
import ru.razbezhkin.crowd.dto.request.EmployeeUpdateDto;
import ru.razbezhkin.crowd.dto.request.RequestEmployeeDto;
import ru.razbezhkin.crowd.exception.EntityNotFoundException;
import ru.razbezhkin.crowd.exception.ValidationException;
import ru.razbezhkin.crowd.mapper.CrowdMapper;
import ru.razbezhkin.crowd.repository.EmployeeRepository;
import ru.razbezhkin.crowd.service.validator.EmployeeValidator;
import ru.razbezhkin.crowd.utils.PartialUpdate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final List<EmployeeValidator> validators;

    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployee() {
        return CrowdMapper.toEmployeeDtoList(employeeRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> getEmployeesByFullNamePredicate(String name) {
        return CrowdMapper.toEmployeeDtoList(employeeRepository.findByName(name));
    }

    @Transactional
    public void createEmployee(RequestEmployeeDto employeeDto) {
        validateNew(employeeDto);
        employeeRepository.save(CrowdMapper.toEmployee(employeeDto));
    }

    @Transactional
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
            .map(CrowdMapper::toEmployeeDto)
            .orElseThrow(() -> new EntityNotFoundException("Employee with id: " + id + " not found"));
    }

    public boolean isExistByLogin(String login) {
        return employeeRepository.existsByLogin(login);
    }

    private void validateNew(RequestEmployeeDto employee) {
        List<String> errors = validators.stream()
            .map(it -> it.validateAndError(employee))
            .filter(it -> !it.isEmpty())
            .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            throw new ValidationException(
                errors.stream().collect(Collectors.joining(" ,", "Errors with create employee - [ ", " ]")));
        }
    }

    private void validateUpdate(EmployeeUpdateDto employeeUpdateDto, Employee employee) {
        List<String> errors = validators.stream()
            .map(it -> it.updateValidateAndError(employeeUpdateDto, employee))
            .filter(it -> !it.isEmpty())
            .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            throw new ValidationException(
                errors.stream().collect(Collectors.joining(" ,", "Errors with create employee - [ ", " ]")));
        }
    }

    @Transactional
    public Employee updateEmployee(Long id, EmployeeUpdateDto updateDto) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Employee with id: " + id + " not found"));

        validateUpdate(updateDto, employee);

        Employee updated = new PartialUpdate<>(employee)
            .updateIfPresent(updateDto.getLogin(), Employee::setLogin)
            .updateIfPresent(updateDto.getFirstname(), Employee::setFirstname)
            .updateIfPresent(updateDto.getLastname(), Employee::setLastname)
            .updateIfPresent(updateDto.getEmail(), Employee::setEmail)
            .updateIfPresent(updateDto.getPhoneNumber(), Employee::setPhoneNumber)
            .getUpdated();
        employeeRepository.save(updated);
        return updated;
    }
}
