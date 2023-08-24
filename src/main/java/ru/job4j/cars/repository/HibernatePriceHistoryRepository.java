package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernatePriceHistoryRepository implements PriceHistoryRepository {

    private final CrudRepository crudRepository;

    @Override
    public boolean delete(int id) {
        return crudRepository.ifChanged(
                "DELETE PriceHistory p WHERE p.id = :pId", Map.of("pId", id));
    }
}
