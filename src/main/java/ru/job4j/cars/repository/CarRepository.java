package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;

import java.util.Optional;

public interface CarRepository {

    boolean save(Car car);

    Optional<Car> findById(int id);

    boolean delete(int id);
}
