package ru.razbezhkin.crowd.ui.components;

import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@SpringComponent
public class ErrorMessageLayout extends HorizontalLayout implements HasText {

    private final Span span;

    public ErrorMessageLayout() {
        this.span = new Span();
        setVisible(false);
        setAlignItems(Alignment.CENTER);

        getStyle().set("color", "var(--lumo-error-text-color)");

        getThemeList().clear();
        getThemeList().add("spacing-s");

        add(VaadinIcon.EXCLAMATION_CIRCLE_O.create(),
            span);
    }

    public boolean isActive() {
        String text = span.getText();
        return text != null && !text.isEmpty();
    }

    @Override
    public void setText(String text) {
        span.setText(text);
        this.setVisible(isActive());
    }
}
