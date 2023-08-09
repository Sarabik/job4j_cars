package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    @Override
    public void save(Post post) {
        crudRepository.run(session -> session.persist(post));
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    @Override
    public void delete(int postId) {
        crudRepository.run(
                "DELETE Post WHERE id = :pId",
                Map.of("pId", postId)
        );
    }

    @Override
    public Collection<Post> findAll() {
        return crudRepository.query(
                "from Post p JOIN FETCH p.priceHistories ORDER BY p.id asc", Post.class
        );
    }

    @Override
    public Optional<Post> findById(int postId) {
        return crudRepository.optional(
                "FROM Post WHERE id = :pId ORDER BY created ASC", Post.class,
                Map.of("pId", postId)
        );
    }

    @Override
    public Collection<Post> findPostsForLast24Hours() {
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        return crudRepository.query(
                "FROM Post p JOIN FETCH p.priceHistories WHERE p.created >= :pDate", Post.class,
                Map.of("pDate", date));
    }

    @Override
    public Collection<Post> findPostsWithPhoto() {
        return crudRepository.query("FROM Post p JOIN FETCH p.priceHistories WHERE p.imageFile IS NOT NULL", Post.class);
    }

    @Override
    public Collection<Post> findPostsByMake(String make) {
        return crudRepository.query("FROM Post p JOIN FETCH p.priceHistories WHERE p.car.carModel.make = :cMake", Post.class,
                Map.of("cMake", make)
        );
    }

}
