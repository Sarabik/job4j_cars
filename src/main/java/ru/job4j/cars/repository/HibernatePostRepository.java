package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    @Override
    public boolean save(Post post) {
        return crudRepository.ifSaved(post);
    }

    @Override
    public boolean update(Post post) {
        return crudRepository.ifChanged(post);
    }

    @Override
    public boolean delete(int postId) {
        return crudRepository.ifChanged(
                "DELETE Post WHERE id = :pId",
                Map.of("pId", postId)
        );
    }

    @Override
    public Optional<Post> findById(int postId) {
        return crudRepository.optional(
                "FROM Post p JOIN FETCH p.priceHistories WHERE p.id = :pId", Post.class,
                Map.of("pId", postId)
        );
    }

    @Override
    public boolean movePostToSold(int id) {
        return crudRepository.ifChanged("UPDATE Post SET isSold = true WHERE id = :pId",
                Map.of("pId", id));
    }

    @Override
    public boolean updateDate(int id) {
        return crudRepository.ifChanged("UPDATE Post SET created = :pDate WHERE id = :pId",
                Map.of("pId", id, "pDate", LocalDateTime.now()));
    }
}
