package ru.job4j.cars.repository;

import ru.job4j.cars.model.ImageFile;

import java.util.Optional;

public interface ImageFileRepository {

    void save(ImageFile imageFile);

    void update(ImageFile imageFile);

    void delete(int id);

    Optional<ImageFile> findById(int id);

}
