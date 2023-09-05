package ru.job4j.cars.service;

import ru.job4j.cars.dto.ImageFileDto;
import ru.job4j.cars.model.Post;

import java.util.Optional;

public interface PostService {

    boolean save(Post post, ImageFileDto imageFileDto);

    boolean update(Post post);

    boolean delete(int postId);

    Optional<Post> findById(int postId);

    boolean movePostToSold(int id);

    boolean updateDate(int id);

}
