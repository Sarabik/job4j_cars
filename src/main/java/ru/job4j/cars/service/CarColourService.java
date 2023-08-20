package ru.job4j.cars.service;

import ru.job4j.cars.model.CarColour;

import java.util.Collection;
import java.util.Optional;

public interface CarColourService {
    Collection<CarColour> findAll();
    Optional<CarColour> findById(int id);
}
