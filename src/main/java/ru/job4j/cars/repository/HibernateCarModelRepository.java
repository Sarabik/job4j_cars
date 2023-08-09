package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarModel;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class HibernateCarModelRepository implements CarModelRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<CarModel> findAll() {
        return crudRepository.query("FROM CarModel ORDER BY id ASC", CarModel.class);
    }
}
