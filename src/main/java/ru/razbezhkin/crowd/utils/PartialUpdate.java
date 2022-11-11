package ru.razbezhkin.crowd.utils;

import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class PartialUpdate<T> {
    private final T entity;

    public <E> PartialUpdate<T> updateIfPresent(DynamicProperty<E> optional, BiConsumer<T, E> consumer) {
        if (optional != null && optional.isPresent()) {
            consumer.accept(entity, optional.getValue());
        }
        return this;
    }

    public T getUpdated() {
        return entity;
    }
}
