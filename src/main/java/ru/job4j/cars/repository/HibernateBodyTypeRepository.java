package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.BodyType;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class HibernateBodyTypeRepository implements BodyTypeRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<BodyType> findAll() {
        return crudRepository.query("FROM BodyType ORDER BY id ASC", BodyType.class);
    }
}
