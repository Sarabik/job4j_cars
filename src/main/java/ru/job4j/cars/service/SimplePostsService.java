package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostsRepository;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@AllArgsConstructor
public class SimplePostsService implements PostsService {

    private final PostsRepository postsRepository;

    @Override
    public Collection<Post> findAllNotSold() {
        return postsRepository.findAllNotSold();
    }

    @Override
    public Collection<Post> findPostsForLast24Hours() {
        return postsRepository.findPostsForLast24Hours();
    }

    @Override
    public Collection<Post> findPostsWithPhoto() {
        return postsRepository.findPostsWithPhoto();
    }

    @Override
    public Collection<Post> findPostsByMake(String make) {
        if (make == null || make.isEmpty()) {
            return postsRepository.findAllNotSold();
        }
        return postsRepository.findPostsByMake(make);
    }

    @Override
    public Collection<Post> findAllActiveByUserId(int id) {
        return postsRepository.findAllActiveByUserId(id);
    }

    @Override
    public Collection<Post> findAllSoldByUserId(int id) {
        return postsRepository.findAllSoldByUserId(id);
    }

    @Override
    public Collection<Post> findPostsByYearInterval(Integer from, Integer until) {
        if (from == null) {
            from = 1900;
        }
        if (until == null) {
            until = LocalDateTime.now().getYear();
        }
        return postsRepository.findPostsByYearInterval(from, until);
    }

    @Override
    public Collection<Post> findPostsByPriceInterval(Long from, Long until) {
        if (from == null) {
            from = 0L;
        }
        if (until == null) {
            until = Long.MAX_VALUE;
        }
        return postsRepository.findPostsByPriceInterval(from, until);
    }
}
