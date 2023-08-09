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
    public void save(Car car) {
        crudRepository.run(session -> session.persist(car));
    }

    @Override
    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                "from Car where id = :cId", Car.class,
                Map.of("cId", id)
        );
    }
}
