package ru.job4j.cars.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Repository
public class HibernatePostsRepository implements PostsRepository {

    private final CrudRepository crudRepository;

    private final String storageDirectory;

    private final String noImage;

    public HibernatePostsRepository(CrudRepository crudRepository,
                                   @Value("${file.directory}") String storageDirectory,
                                   @Value("${file.default.name}") String noImage) {
        this.crudRepository = crudRepository;
        this.storageDirectory = storageDirectory;
        this.noImage = noImage;
    }

    @Override
    public Collection<Post> findAllNotSold() {
        return crudRepository.query(
                "from Post WHERE isSold = false ORDER BY created desc", Post.class
        );
    }

    @Override
    public Collection<Post> findAll() {
        return crudRepository.query(
                "from Post", Post.class
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
    public Collection<Post> findPostsByYearInterval(int from, int until) {
        return crudRepository.query("FROM Post p WHERE p.car.carYear >= :dFrom AND p.car.carYear <= :dUntil", Post.class,
                Map.of("dFrom", from, "dUntil", until)
        );
    }

    @Override
    public Collection<Post> findPostsByPriceInterval(long from, long until) {
        return crudRepository.query("FROM Post p WHERE p.price >= :dFrom AND p.price <= :dUntil", Post.class,
                Map.of("dFrom", from, "dUntil", until)
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
}
