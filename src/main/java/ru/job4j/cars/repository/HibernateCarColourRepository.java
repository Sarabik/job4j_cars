package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarColour;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateCarColourRepository implements CarColourRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<CarColour> findAll() {
        return crudRepository.query("FROM CarColour ORDER BY id ASC", CarColour.class);
    }

    @Override
    public Optional<CarColour> findById(int id) {
        return crudRepository.optional("FROM CarColour WHERE id = :cId", CarColour.class,
                Map.of("cId", id));
    }
}
