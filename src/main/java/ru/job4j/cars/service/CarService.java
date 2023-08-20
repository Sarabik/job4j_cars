package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;

import java.util.Optional;

public interface CarService {

    void save(Car car);

    Optional<Car> findById(int id);

    void delete(int id);

}
