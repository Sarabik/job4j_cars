package ru.job4j.cars.service;

import ru.job4j.cars.model.EngineSize;

import java.util.Collection;
import java.util.Optional;

public interface EngineSizeService {
    Collection<EngineSize> findAll();
    Optional<EngineSize> findById(int id);
}
