package ru.job4j.cars.repository;

import ru.job4j.cars.model.CarColour;

import java.util.Collection;
import java.util.Optional;

public interface CarColourRepository {
    Collection<CarColour> findAll();
    Optional<CarColour> findById(int id);
}
