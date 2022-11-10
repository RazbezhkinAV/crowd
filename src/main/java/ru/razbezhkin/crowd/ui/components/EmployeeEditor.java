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
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.razbezhkin.crowd.dto.EmployeeDto;
import ru.razbezhkin.crowd.exception.EmployeeException;
import ru.razbezhkin.crowd.service.EmployeeService;
import ru.razbezhkin.crowd.ui.validator.PhoneNumberValidator;

import javax.annotation.PostConstruct;
import java.util.Locale;

@SpringComponent
@UIScope
@RequiredArgsConstructor
public class EmployeeEditor extends VerticalLayout implements KeyNotifier {

    private final EmployeeService employeeService;

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

        configureLoginField();
        configureBinder();

        add(login,
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
        if(isLoginFree()) {
            employeeService.saveEmployee(employee);
            changeHandler.onChange();
        }else {
            login.setReadOnly(false);
            login.setErrorMessage("This login is busy, please replace it");
        }
    }

    private boolean isLoginFree() {
        try {
            employeeService.getEmployeeByLogin(login.getValue());
            return false;
        }catch (EmployeeException exception){
            return true;
        }
    }

    private void configureLoginField() {
        login.setReadOnly(true);

        firstName.setValueChangeMode(ValueChangeMode.LAZY);
        firstName.addValueChangeListener(value -> {
            if (value.getValue().isEmpty()) {
                login.clear();
            } else {
                char firstChar = value.getValue().toLowerCase(Locale.ROOT).charAt(0);
                login.setValue(firstChar + ".");
            }
        });

        lastName.setValueChangeMode(ValueChangeMode.LAZY);
        lastName.addValueChangeListener(value -> {
            if (!value.getValue().isEmpty()) {
                String lowerLastName = value.getValue().toLowerCase(Locale.ROOT);
                String currentLogin = login.getValue();
                String[] split = currentLogin.split("\\.");
                if (split.length > 1) {
                    login.setValue(split[0] + "." + lowerLastName);
                } else {
                    login.setValue(currentLogin + lowerLastName);
                }
            }
        });
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
        } catch (EmployeeException exc) {
            employee = employeeDto;
        }
    }
}
