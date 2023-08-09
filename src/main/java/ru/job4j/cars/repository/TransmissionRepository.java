package ru.job4j.cars.repository;

import ru.job4j.cars.model.Transmission;

import java.util.Collection;

public interface TransmissionRepository {
    Collection<Transmission> findAll();
}
