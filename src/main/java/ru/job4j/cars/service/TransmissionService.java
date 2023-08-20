package ru.job4j.cars.service;

import ru.job4j.cars.model.Transmission;

import java.util.Collection;
import java.util.Optional;

public interface TransmissionService {
    Collection<Transmission> findAll();
    Optional<Transmission> findById(int id);
}
