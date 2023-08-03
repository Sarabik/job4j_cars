package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostRepository {

    void save(Post post);

    void update(Post post);

    void delete(int postId);

    Collection<Post> findAll();

    Optional<Post> findById(int postId);

}
