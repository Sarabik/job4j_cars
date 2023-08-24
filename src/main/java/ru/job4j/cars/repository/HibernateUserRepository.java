package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    @Override
    public boolean save(User user) {
        return crudRepository.ifSaved(user);
    }

    @Override
    public boolean update(User user) {
        return crudRepository.ifChanged(user);
    }

    @Override
    public boolean delete(int userId) {
        return crudRepository.ifChanged(
                "DELETE User WHERE id = :fId",
                Map.of("fId", userId)
        );
    }

    @Override
    public List<User> findAllOrderById() {
        return crudRepository.query(
                "from User order by id asc", User.class
        );
    }

    @Override
    public Optional<User> findById(int userId) {
        return crudRepository.optional(
                "from User where id = :fId", User.class,
                Map.of("fId", userId)
        );
    }

    @Override
    public List<User> findByLikeEmail(String key) {
        return crudRepository.query(
                "FROM User WHERE LOWER(email) LIKE LOWER(:fKey)", User.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return crudRepository.optional(
                "FROM User WHERE email = :fEmail AND password = :fPassword", User.class,
                Map.of("fEmail", email, "fPassword", password)
        );
    }
}
