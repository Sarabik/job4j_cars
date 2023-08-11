package ru.job4j.cars.repository;

import ru.job4j.cars.model.EngineSize;

import java.util.Collection;
import java.util.Optional;

public interface EngineSizeRepository {
    Collection<EngineSize> findAll();
    Optional<EngineSize> findById(int id);
}
