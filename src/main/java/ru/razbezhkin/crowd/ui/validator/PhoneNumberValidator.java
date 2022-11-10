package ru.razbezhkin.crowd.ui.validator;

import com.vaadin.flow.data.validator.RegexpValidator;

public class PhoneNumberValidator extends RegexpValidator {

    private static final String PATTERN = "^\\+?7(9\\d{9})$";

    public PhoneNumberValidator(String errorMessage) {
        super(errorMessage, PATTERN, true);
    }

}
