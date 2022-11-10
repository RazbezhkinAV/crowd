package ru.razbezhkin.crowd.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import ru.razbezhkin.crowd.dto.EmployeeDto;
import ru.razbezhkin.crowd.service.EmployeeService;
import ru.razbezhkin.crowd.ui.components.EmployeeEditor;

import javax.annotation.PostConstruct;

@Route("/")
@PageTitle("Employees")
@RequiredArgsConstructor
public class MainUI extends HorizontalLayout {

    private final EmployeeService employeeService;
    private final Grid<EmployeeDto> grid = new Grid<>(EmployeeDto.class,false);
    private final TextField filter = new TextField("", "Type to filter");
    private final Button addNewEmpBut = new Button("Add new Employee");
    private final EmployeeEditor employeeEditor;

    @PostConstruct
    public void init() {
        setupFilter();
        initGrid();
        employeeEditor.setWidth("20%");
        add(new VerticalLayout(new HorizontalLayout(filter, addNewEmpBut), grid), employeeEditor);
    }

    private void setupFilter() {
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showEmployee(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> employeeEditor.edit(e.getValue()));

        addNewEmpBut.addClickListener(e -> employeeEditor.edit(new EmployeeDto()));

        employeeEditor.setChangeHandler(() -> {
            employeeEditor.setVisible(false);
            showEmployee(filter.getValue());
        });
    }

    private void showEmployee(String name) {
        if (name.isEmpty()) {
            grid.setItems(employeeService.getAllEmployee());
        } else {
            grid.setItems(employeeService.getEmployeesByName(name));
        }
    }

    private void initGrid() {
        grid.setItems(employeeService.getAllEmployee());

        grid.addColumn(EmployeeDto::getLogin)
                .setHeader("Login");

        grid.addColumn(EmployeeDto::getFirstname)
                .setHeader("First Name");

        grid.addColumn(EmployeeDto::getLastname)
                .setHeader("Last Name");

        grid.addColumn(EmployeeDto::getPhoneNumber)
                .setHeader("Phone Number");

        grid.addColumn(EmployeeDto::getEmail)
                .setHeader("Email");
    }
}
