package ru.job4j.cars.service;

import ru.job4j.cars.dto.ImageFileDto;
import ru.job4j.cars.model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostService {

    void save(Post post, ImageFileDto imageFileDto);

    void update(Post post);

    void delete(int postId);

    Collection<Post> findAllNotSold();

    Optional<Post> findById(int postId);

    Collection<Post> findPostsForLast24Hours();

    Collection<Post> findPostsWithPhoto();

    Collection<Post> findPostsByMake(String make);

    Collection<Post> findAllActiveByUserId(int id);

    Collection<Post> findAllSoldByUserId(int id);

    Collection<Post> findPostsByYearInterval(Integer from, Integer until);

    Collection<Post> findPostsByPriceInterval(Long from, Long until);

    void movePostToSold(int id);

    void updateDate(int id);

}
