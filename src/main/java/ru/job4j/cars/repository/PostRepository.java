package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.Optional;

public interface PostRepository {

    boolean save(Post post);

    boolean update(Post post);

    boolean delete(int postId);

    Optional<Post> findById(int postId);

    boolean movePostToSold(int id);

    boolean updateDate(int id);
}
