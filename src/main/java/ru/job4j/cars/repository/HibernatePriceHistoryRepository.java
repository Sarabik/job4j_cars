package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernatePriceHistoryRepository implements PriceHistoryRepository {

    private final CrudRepository crudRepository;

    @Override
    public void delete(int id) {
        crudRepository.run(
                "DELETE PriceHistory p WHERE p.id = :pId", Map.of("pId", id));
    }
}
