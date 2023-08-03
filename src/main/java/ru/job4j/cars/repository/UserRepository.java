package ru.job4j.cars.repository;

import ru.job4j.cars.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    void update(User user);

    void delete(int userId);

    Collection<User> findAllOrderById();

    Optional<User> findById(int userId);

    Collection<User> findByLikeLogin(String key);

    Optional<User> findByLogin(String login);
}
