package ru.job4j.cars.repository;

import ru.job4j.cars.model.ImageFile;

import java.util.Optional;

public interface ImageFileRepository {

    boolean save(ImageFile imageFile);

    boolean delete(int id);

    Optional<ImageFile> findById(int id);

    ImageFile getDefault();

}
