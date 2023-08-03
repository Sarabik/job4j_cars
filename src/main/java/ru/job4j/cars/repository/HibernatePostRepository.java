package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

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
                "from Post order by id asc", Post.class
        );
    }

    @Override
    public Optional<Post> findById(int postId) {
        return crudRepository.optional(
                "from Post where id = :pId", Post.class,
                Map.of("pId", postId)
        );
    }
}
