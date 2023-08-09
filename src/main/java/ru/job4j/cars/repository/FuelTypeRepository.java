package ru.job4j.cars.repository;

import ru.job4j.cars.model.FuelType;

import java.util.Collection;

public interface FuelTypeRepository {
    Collection<FuelType> findAll();
}
