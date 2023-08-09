package ru.job4j.cars.repository;

import ru.job4j.cars.model.EngineSize;

import java.util.Collection;

public interface EngineSizeRepository {
    Collection<EngineSize> findAll();
}
