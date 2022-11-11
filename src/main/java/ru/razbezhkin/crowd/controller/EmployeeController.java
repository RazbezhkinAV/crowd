package ru.razbezhkin.crowd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.razbezhkin.crowd.domain.Employee;
import ru.razbezhkin.crowd.dto.EmployeeDto;
import ru.razbezhkin.crowd.dto.request.EmployeeUpdateDto;
import ru.razbezhkin.crowd.dto.request.RequestEmployeeDto;
import ru.razbezhkin.crowd.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/v0/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> findAll() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    public EmployeeDto findEmployeeByLogin(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public void createEmployee(@RequestBody RequestEmployeeDto requestEmployeeDto) {
        employeeService.createEmployee(requestEmployeeDto);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody EmployeeUpdateDto requestEmployeeDto) {
        return employeeService.updateEmployee(id, requestEmployeeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
    }

}
