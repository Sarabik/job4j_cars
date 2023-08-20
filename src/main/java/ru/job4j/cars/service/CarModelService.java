package ru.job4j.cars.service;

import ru.job4j.cars.model.CarModel;

import java.util.Collection;
import java.util.Optional;

public interface CarModelService {
    Collection<CarModel> findAll();
    Optional<CarModel> findById(int id);
    Collection<String> findAllCarMadeCollection();
    Collection<CarModel> findByCarMake(String make);
}
