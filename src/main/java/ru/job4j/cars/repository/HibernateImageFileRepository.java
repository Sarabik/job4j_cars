package ru.job4j.cars.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.ImageFile;

import java.util.Map;
import java.util.Optional;

@Repository
public class HibernateImageFileRepository implements ImageFileRepository {

    private final CrudRepository crudRepository;

    private final String noImage;

    public HibernateImageFileRepository(CrudRepository crudRepository,
                                   @Value("${file.default.name}") String noImage) {
        this.crudRepository = crudRepository;
        this.noImage = noImage;
    }

    @Override
    public void save(ImageFile imageFile) {
        crudRepository.run(session -> session.persist(imageFile));
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

    @Override
    public ImageFile getDefault() {
        return crudRepository.optional(
                "FROM ImageFile where fileName = :fName", ImageFile.class,
                Map.of("fName", noImage)
        ).get();
    }
}
