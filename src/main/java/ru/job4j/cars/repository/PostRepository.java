package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostRepository {

    void save(Post post);

    void update(Post post);

    void delete(int postId);

    Collection<Post> findAllNotSold();

    Collection<Post> findAll();

    Optional<Post> findById(int postId);

    Collection<Post> findPostsForLast24Hours();

    Collection<Post> findPostsWithPhoto();

    Collection<Post> findPostsByMake(String make);

    Collection<Post> findAllActiveByUserId(int id);

    Collection<Post> findAllSoldByUserId(int id);

    Collection<Post> findPostsByYearInterval(int from, int until);

    Collection<Post> findPostsByPriceInterval(long from, long until);

    void movePostToSold(int id);

    void updateDate(int id);
}
