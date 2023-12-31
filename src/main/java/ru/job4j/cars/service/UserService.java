package ru.job4j.cars.service;

import ru.job4j.cars.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    boolean save(User user);

    boolean update(User user);

    boolean delete(int userId);

    Collection<User> findAllOrderById();

    Optional<User> findById(int userId);

    Collection<User> findByLikeEmail(String key);

    Optional<User> findByEmailAndPassword(String email, String password);
}
