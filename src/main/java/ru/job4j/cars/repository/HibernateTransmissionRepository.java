package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Transmission;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTransmissionRepository implements TransmissionRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Transmission> findAll() {
        return crudRepository.query("FROM Transmission ORDER BY id ASC", Transmission.class);
    }

    @Override
    public Optional<Transmission> findById(int id) {
        return crudRepository.optional("FROM Transmission WHERE id = :tId", Transmission.class,
                Map.of("tId", id));
    }
}
