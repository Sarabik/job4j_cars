package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;

import java.util.Optional;

public interface CarService {

    boolean save(Car car);

    Optional<Car> findById(int id);

    boolean delete(int id);

}
