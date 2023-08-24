package ru.job4j.cars.repository;

import ru.job4j.cars.model.CarModel;

import java.util.Collection;
import java.util.Optional;

public interface CarModelRepository {
    Collection<CarModel> findAll();
    Optional<CarModel> findById(int id);
}
