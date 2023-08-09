package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarColour;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class HibernateCarColourRepository implements CarColourRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<CarColour> findAll() {
        return crudRepository.query("FROM CarColour ORDER BY id ASC", CarColour.class);
    }
}
