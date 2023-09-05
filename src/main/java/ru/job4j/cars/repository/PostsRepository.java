package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.Collection;

public interface PostsRepository {

    Collection<Post> findAllNotSold();

    Collection<Post> findAll();

    Collection<Post> findPostsForLast24Hours();

    Collection<Post> findPostsWithPhoto();

    Collection<Post> findPostsByMake(String make);

    Collection<Post> findAllActiveByUserId(int id);

    Collection<Post> findAllSoldByUserId(int id);

    Collection<Post> findPostsByYearInterval(int from, int until);

    Collection<Post> findPostsByPriceInterval(long from, long until);

}
