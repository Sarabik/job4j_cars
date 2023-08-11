package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.BodyType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateBodyTypeRepository implements BodyTypeRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<BodyType> findAll() {
        return crudRepository.query("FROM BodyType ORDER BY id ASC", BodyType.class);
    }

    @Override
    public Optional<BodyType> findById(int id) {
        return crudRepository.optional("FROM BodyType WHERE id = :bId", BodyType.class,
                Map.of("bId", id));
    }
}
