package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.ImageFileDto;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {

    private final PostRepository postRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final ImageFileService imageFileService;

    @Override
    public void save(Post post, ImageFileDto imageFileDto) {
        post.setCreated(LocalDateTime.now());
        post.setPriceHistories(List.of(addNewPriceHistory(post)));
        post.setImageFile(imageFileService.save(imageFileDto));
        postRepository.save(post);
    }

    private PriceHistory addNewPriceHistory(Post post) {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(post.getPrice());
        priceHistory.setCreated(post.getCreated());
        return priceHistory;
    }

    @Override
    public void update(Post post) {
        post.setCreated(LocalDateTime.now());
        Optional<Post> opt = postRepository.findById(post.getId());
        if (opt.isPresent()) {
            Post postOld = opt.get();
            if (postOld.getPrice() != post.getPrice()) {
                postOld.getPriceHistories().add(addNewPriceHistory(post));
            }
            post.setPriceHistories(postOld.getPriceHistories());
            post.setUser(postOld.getUser());
            post.setCar(postOld.getCar());
            post.setImageFile(postOld.getImageFile());
            postRepository.update(post);
        }
    }

    @Override
    public void delete(int postId) {
        Optional<Post> post = findById(postId);
        if (post.isPresent() && !post.get().getPriceHistories().isEmpty()) {
            post.get().getPriceHistories().forEach(p -> priceHistoryRepository.delete(p.getId()));
        }
        postRepository.delete(postId);
        imageFileService.delete(post.get().getImageFile().getId());
    }

    @Override
    public Collection<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(int postId) {
        return postRepository.findById(postId);
    }

    @Override
    public Collection<Post> findPostsForLast24Hours() {
        return postRepository.findPostsForLast24Hours();
    }

    @Override
    public Collection<Post> findPostsWithPhoto() {
        return postRepository.findPostsWithPhoto();
    }

    @Override
    public Collection<Post> findPostsByMake(String make) {
        return postRepository.findPostsByMake(make);
    }

    @Override
    public Collection<Post> findAllActiveByUserId(int id) {
        return postRepository.findAllActiveByUserId(id);
    }

    @Override
    public Collection<Post> findAllSoldByUserId(int id) {
        return postRepository.findAllSoldByUserId(id);
    }

    @Override
    public void movePostToSold(int id) {
        postRepository.movePostToSold(id);
    }

    @Override
    public void updateDate(int id) {
        postRepository.updateDate(id);
    }
}
