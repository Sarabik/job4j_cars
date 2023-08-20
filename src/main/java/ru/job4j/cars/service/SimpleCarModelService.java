package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarModel;
import ru.job4j.cars.repository.CarModelRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleCarModelService implements CarModelService {
    private final CarModelRepository carModelRepository;

    @Override
    public Collection<CarModel> findAll() {
        return carModelRepository.findAll();
    }

    @Override
    public Optional<CarModel> findById(int id) {
        return carModelRepository.findById(id);
    }

    @Override
    public Collection<String> findAllCarMadeCollection() {
        Set<String> carMadeSet = new HashSet<>();
        findAll().forEach(carModel -> carMadeSet.add(carModel.getMake()));
        return carMadeSet;
    }

    @Override
    public Collection<CarModel> findByCarMake(String make) {
        return carModelRepository.findByCarMake(make);
    }
}
