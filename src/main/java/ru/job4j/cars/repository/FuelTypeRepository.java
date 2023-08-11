package ru.job4j.cars.repository;

import ru.job4j.cars.model.FuelType;

import java.util.Collection;
import java.util.Optional;

public interface FuelTypeRepository {
    Collection<FuelType> findAll();
    Optional<FuelType> findById(int id);
}
