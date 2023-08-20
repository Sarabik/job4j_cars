package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.BodyType;
import ru.job4j.cars.repository.BodyTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleBodyTypeService implements BodyTypeService {
    private final BodyTypeRepository bodyTypeRepository;

    @Override
    public Collection<BodyType> findAll() {
        return bodyTypeRepository.findAll();
    }

    @Override
    public Optional<BodyType> findById(int id) {
        return bodyTypeRepository.findById(id);
    }
}
