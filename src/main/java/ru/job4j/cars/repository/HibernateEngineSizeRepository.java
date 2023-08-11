package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.EngineSize;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateEngineSizeRepository implements EngineSizeRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<EngineSize> findAll() {
        return crudRepository.query("FROM EngineSize ORDER BY id ASC", EngineSize.class);
    }

    @Override
    public Optional<EngineSize> findById(int id) {
        return crudRepository.optional("FROM EngineSize WHERE id = :eId", EngineSize.class,
                Map.of("eId", id));
    }
}
