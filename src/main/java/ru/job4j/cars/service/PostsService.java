package ru.job4j.cars.service;

import ru.job4j.cars.model.Post;

import java.util.Collection;

public interface PostsService {

    Collection<Post> findAllNotSold();

    Collection<Post> findPostsForLast24Hours();

    Collection<Post> findPostsWithPhoto();

    Collection<Post> findPostsByMake(String make);

    Collection<Post> findAllActiveByUserId(int id);

    Collection<Post> findAllSoldByUserId(int id);

    Collection<Post> findPostsByYearInterval(Integer from, Integer until);

    Collection<Post> findPostsByPriceInterval(Long from, Long until);
}
