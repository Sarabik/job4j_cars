package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.ImageFile;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateImageFileRepository implements ImageFileRepository {

    private final CrudRepository crudRepository;

    @Override
    public void save(ImageFile imageFile) {
        crudRepository.run(session -> session.persist(imageFile));
    }

    @Override
    public void update(ImageFile imageFile) {
        crudRepository.run(session -> session.merge(imageFile));
    }

    @Override
    public void delete(int id) {
        crudRepository.run(
                "DELETE ImageFile WHERE id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public Optional<ImageFile> findById(int id) {
        return crudRepository.optional(
                "from ImageFile where id = :fId", ImageFile.class,
                Map.of("fId", id)
        );
    }
}
