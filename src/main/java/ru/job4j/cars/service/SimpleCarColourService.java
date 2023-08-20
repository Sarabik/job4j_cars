package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarColour;
import ru.job4j.cars.repository.CarColourRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCarColourService implements CarColourService {

    private final CarColourRepository carColourRepository;

    @Override
    public Collection<CarColour> findAll() {
        return carColourRepository.findAll();
    }

    @Override
    public Optional<CarColour> findById(int id) {
        return carColourRepository.findById(id);
    }
}
