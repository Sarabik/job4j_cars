package ru.job4j.cars.service;

import ru.job4j.cars.dto.ImageFileDto;
import ru.job4j.cars.model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostService {

    boolean save(Post post, ImageFileDto imageFileDto);

    boolean update(Post post);

    boolean delete(int postId);

    Collection<Post> findAllNotSold();

    Optional<Post> findById(int postId);

    Collection<Post> findPostsForLast24Hours();

    Collection<Post> findPostsWithPhoto();

    Collection<Post> findPostsByMake(String make);

    Collection<Post> findAllActiveByUserId(int id);

    Collection<Post> findAllSoldByUserId(int id);

    Collection<Post> findPostsByYearInterval(Integer from, Integer until);

    Collection<Post> findPostsByPriceInterval(Long from, Long until);

    boolean movePostToSold(int id);

    boolean updateDate(int id);

}
