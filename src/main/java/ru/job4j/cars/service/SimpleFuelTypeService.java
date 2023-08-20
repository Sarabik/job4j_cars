package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.FuelType;
import ru.job4j.cars.repository.FuelTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleFuelTypeService implements FuelTypeService {
    private final FuelTypeRepository fuelTypeRepository;

    @Override
    public Collection<FuelType> findAll() {
        return fuelTypeRepository.findAll();
    }

    @Override
    public Optional<FuelType> findById(int id) {
        return fuelTypeRepository.findById(id);
    }
}
