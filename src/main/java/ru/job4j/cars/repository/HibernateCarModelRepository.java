package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarModel;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateCarModelRepository implements CarModelRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<CarModel> findAll() {
        return crudRepository.query("FROM CarModel ORDER BY id ASC", CarModel.class);
    }

    @Override
    public Optional<CarModel> findById(int id) {
        return crudRepository.optional("FROM CarModel WHERE id = :mId", CarModel.class,
                Map.of("mId", id));
    }
}
