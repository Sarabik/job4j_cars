package ru.job4j.cars.service;

import ru.job4j.cars.model.FuelType;

import java.util.Collection;
import java.util.Optional;

public interface FuelTypeService {
    Collection<FuelType> findAll();
    Optional<FuelType> findById(int id);
}
