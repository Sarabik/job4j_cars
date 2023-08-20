package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCarService implements CarService {
    private final CarRepository carRepository;

    @Override
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        carRepository.delete(id);
    }
}
