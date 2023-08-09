package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.FuelType;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class HibernateFuelTypeRepository implements FuelTypeRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<FuelType> findAll() {
        return crudRepository.query("FROM FuelType ORDER BY id ASC", FuelType.class);
    }
}
