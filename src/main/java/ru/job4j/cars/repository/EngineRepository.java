package ru.job4j.cars.repository;

import ru.job4j.cars.model.Engine;

import java.util.Optional;

public interface EngineRepository {

    void save(Engine engine);

    Optional<Engine> findById(int id);

}
