package ru.job4j.cars.repository;

import ru.job4j.cars.model.BodyType;

import java.util.Collection;

public interface BodyTypeRepository {
    Collection<BodyType> findAll();
}
