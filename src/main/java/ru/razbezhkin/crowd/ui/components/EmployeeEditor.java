package ru.razbezhkin.crowd.ui.components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.razbezhkin.crowd.dto.EmployeeDto;
import ru.razbezhkin.crowd.exception.EntityNotFoundException;
import ru.razbezhkin.crowd.service.EmployeeService;
import ru.razbezhkin.crowd.ui.validator.PhoneNumberValidator;

import javax.annotation.PostConstruct;
import java.util.Locale;

@SpringComponent
@UIScope
@RequiredArgsConstructor
public class EmployeeEditor extends VerticalLayout implements KeyNotifier {

    private final EmployeeService employeeService;
    private final ErrorMessageLayout errorMessageLayout;

    private final TextField login = new TextField("Login");
    private final TextField firstName = new TextField("First name");
    private final TextField email = new TextField("Email");
    private final TextField phoneNumber = new TextField("Phone number");
    private final TextField lastName = new TextField("Last name");

    private final Button save = new Button("Save", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Cancel");
    private final Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private final Binder<EmployeeDto> binder = new Binder<>(EmployeeDto.class);

    private EmployeeDto employee;

    @Setter
    private ChangeHandler changeHandler;

    @PostConstruct
    public void init() {
        addKeyPressListener(Key.ENTER, e -> save());

        setSpacing(true);
        setVisible(false);

        login.setVisible(false);
        configureBinder();

        add(login,
            errorMessageLayout,
            firstName,
            lastName,
            phoneNumber,
            email,
            createActionLayout());
    }

    private void configureBinder() {
        String fieldMustBeSet = "Field must be set";

        binder.forField(login)
            .bind(EmployeeDto::getLogin, EmployeeDto::setLogin);

        binder.forField(firstName)
            .asRequired(fieldMustBeSet)
            .bind(EmployeeDto::getFirstname, EmployeeDto::setFirstname);

        binder.forField(lastName)
            .asRequired(fieldMustBeSet)
            .bind(EmployeeDto::getLastname, EmployeeDto::setLastname);

        binder.forField(phoneNumber)
            .asRequired(fieldMustBeSet)
            .withValidator(new PhoneNumberValidator("Phone number must be like +7 / 7 (9xx) xxx xx x"))
            .bind(EmployeeDto::getPhoneNumber, EmployeeDto::setPhoneNumber);

        binder.forField(email)
            .asRequired(fieldMustBeSet)
            .withValidator(new EmailValidator("Invalid email"))
            .bind(EmployeeDto::getEmail, EmployeeDto::setEmail);
    }

    private HorizontalLayout createActionLayout() {
        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> edit(employee));

        return new HorizontalLayout(save, cancel, delete);
    }

    private void delete() {
        employeeService.deleteEmployee(employee);
        changeHandler.onChange();
    }

    private void save() {
        loginGenerator(firstName.getValue(), lastName.getValue());
        if (isLoginFree()) {
            employeeService.saveEmployee(employee);
            changeHandler.onChange();
        } else {
            login.setVisible(true);
        }
    }

    private boolean isLoginFree() {
        try {
            employeeService.getEmployeeByLogin(login.getValue());
            errorMessageLayout.setText("This login is busy, please replace it");
            return false;
        } catch (EntityNotFoundException exception) {
            errorMessageLayout.setText("");
            return true;
        }
    }

    private void loginGenerator(String firstName, String lastName) {
        login.setValue(firstName.toLowerCase(Locale.ROOT).charAt(0) + "." + lastName.toLowerCase(Locale.ROOT));
    }

    public void edit(EmployeeDto newEmp) {
        if (newEmp == null) {
            setVisible(false);
            return;
        }


        final boolean persisted = newEmp.getLogin() != null;
        if (persisted) {
            setEmployee(newEmp);
        } else {
            employee = new EmployeeDto();
        }

        cancel.setVisible(persisted);
        binder.setBean(employee);

        binder.addValueChangeListener(value ->
                                          save.setEnabled(binder.isValid())
        );

        setVisible(true);

        firstName.focus();
    }

    private void setEmployee(EmployeeDto employeeDto) {
        try {
            employee = employeeService.getEmployeeByLogin(employeeDto.getLogin());
        } catch (EntityNotFoundException exc) {
            employee = employeeDto;
        }
    }
}
