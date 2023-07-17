package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final CrudRepository crudRepository;

    public User create(User user) {
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    public void delete(int userId) {
        crudRepository.run(
                "DELETE User WHERE id = :fId",
                Map.of("fId", userId)
        );
    }

    public List<User> findAllOrderById() {
        return crudRepository.query(
                "from User order by id asc", User.class
        );
    }

    public Optional<User> findById(int userId) {
        return crudRepository.optional(
                "from User where id = :fId", User.class,
                Map.of("fId", userId)
        );
    }

    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                "FROM User WHERE LOWER(login) LIKE LOWER(:fKey)", User.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                "FROM User WHERE login = :fLogin", User.class,
                Map.of("fLogin", login)
        );
    }
}
