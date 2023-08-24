package ru.job4j.cars.repository;

import ru.job4j.cars.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    boolean save(User user);

    boolean update(User user);

    boolean delete(int userId);

    Collection<User> findAllOrderById();

    Optional<User> findById(int userId);

    Collection<User> findByLikeEmail(String key);

    Optional<User> findByEmailAndPassword(String login, String password);
}
