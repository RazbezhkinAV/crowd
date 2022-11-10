package ru.razbezhkin.crowd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.razbezhkin.crowd.dto.EmployeeDto;
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

    @GetMapping("/{login}")
    public EmployeeDto findEmployeeByLogin(@PathVariable String login) {
        return employeeService.getEmployeeByLogin(login);
    }

    @PostMapping
    public void createEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.saveEmployee(employeeDto);
    }


}
