package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateCarRepository implements CarRepository {

    private final CrudRepository crudRepository;

    @Override
    public boolean save(Car car) {
        return crudRepository.ifSaved(car);
    }

    @Override
    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                "from Car where id = :cId", Car.class,
                Map.of("cId", id)
        );
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.ifChanged("DELETE Car WHERE id = :cId",
                Map.of("cId", id)
        );
    }
}
