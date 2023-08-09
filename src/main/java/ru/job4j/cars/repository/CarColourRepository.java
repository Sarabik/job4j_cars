package ru.job4j.cars.repository;

import ru.job4j.cars.model.CarColour;

import java.util.Collection;

public interface CarColourRepository {
    Collection<CarColour> findAll();
}
