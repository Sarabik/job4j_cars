package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
public class HibernatePostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    private final String storageDirectory;

    private final String noImage;

    public HibernatePostRepository(CrudRepository crudRepository,
                                   @Value("${file.directory}") String storageDirectory,
                                   @Value("${file.default.name}") String noImage) {
        this.crudRepository = crudRepository;
        this.storageDirectory = storageDirectory;
        this.noImage = noImage;
    }

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
                "from Post WHERE isSold = false ORDER BY created desc", Post.class
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
    public Collection<Post> findPostsForLast24Hours() {
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        return crudRepository.query(
                "FROM Post WHERE created >= :pDate", Post.class,
                Map.of("pDate", date));
    }

    @Override
    public Collection<Post> findPostsWithPhoto() {
        return crudRepository.query("FROM Post p WHERE p.imageFile.path IS NOT :pPath", Post.class,
                Map.of("pPath", storageDirectory + java.io.File.separator + noImage));
    }

    @Override
    public Collection<Post> findPostsByMake(String make) {
        return crudRepository.query("FROM Post p WHERE p.car.carModel.make = :cMake", Post.class,
                Map.of("cMake", make)
        );
    }

    @Override
    public Collection<Post> findAllActiveByUserId(int id) {
        return crudRepository.query(
                "from Post p WHERE p.isSold = false AND p.user.id = :pId ORDER BY created desc", Post.class,
                Map.of("pId", id)
        );
    }

    @Override
    public Collection<Post> findAllSoldByUserId(int id) {
        return crudRepository.query(
                "from Post p WHERE p.isSold = true AND p.user.id = :pId ORDER BY created desc", Post.class,
                Map.of("pId", id)
        );
    }

    @Override
    public void movePostToSold(int id) {
        crudRepository.run("UPDATE Post SET isSold = true WHERE id = :pId",
                Map.of("pId", id));
    }

    @Override
    public void updateDate(int id) {
        crudRepository.run("UPDATE Post SET created = :pDate WHERE id = :pId",
                Map.of("pId", id, "pDate", LocalDateTime.now()));
    }
}
