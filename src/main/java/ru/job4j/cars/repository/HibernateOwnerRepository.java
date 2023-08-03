package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateOwnerRepository implements OwnerRepository {

    private final CrudRepository crudRepository;

    @Override
    public void save(Owner owner) {
        crudRepository.run(session -> session.persist(owner));
    }

    @Override
    public Optional<Owner> findById(int id) {
        return Optional.empty();
    }
}
