package ru.razbezhkin.crowd.utils;

public class DynamicProperty<T> {
    private final T value;

    public DynamicProperty() {
        value = null;
    }

    public DynamicProperty(T value) {
        this.value = value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public T getValue() {
        return value;
    }
}
