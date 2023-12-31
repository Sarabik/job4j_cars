package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.ImageFileDto;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {

    private final PostRepository postRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final ImageFileService imageFileService;
    private final CarService carService;

    @Override
    public boolean save(Post post, ImageFileDto imageFileDto) {
        post.setCreated(LocalDateTime.now());
        post.setPriceHistories(List.of(addNewPriceHistory(post)));
        if (imageFileDto.getName().isEmpty()) {
            post.setImageFile(imageFileService.getDefault());
        } else {
            post.setImageFile(imageFileService.save(imageFileDto));
        }
        return postRepository.save(post);
    }

    private PriceHistory addNewPriceHistory(Post post) {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(post.getPrice());
        priceHistory.setCreated(post.getCreated());
        return priceHistory;
    }

    @Override
    public boolean update(Post post) {
        boolean ifUpdated = false;
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
            ifUpdated = postRepository.update(post);
        }
        return ifUpdated;
    }

    @Override
    public boolean delete(int postId) {
        Optional<Post> optPost = findById(postId);
        if (optPost.isEmpty()) {
            return false;
        }
        Post post = optPost.get();
        if (!post.getPriceHistories().isEmpty()) {
            post.getPriceHistories().forEach(p -> priceHistoryRepository.delete(p.getId()));
        }
        boolean ifDeleted = postRepository.delete(postId);
        if (ifDeleted) {
            carService.delete(post.getCar().getId());
            imageFileService.delete(post.getImageFile().getId());
        }
        return ifDeleted;
    }

    @Override
    public Optional<Post> findById(int postId) {
        return postRepository.findById(postId);
    }

    @Override
    public boolean movePostToSold(int id) {
       return postRepository.movePostToSold(id);
    }

    @Override
    public boolean updateDate(int id) {
       return postRepository.updateDate(id);
    }
}
