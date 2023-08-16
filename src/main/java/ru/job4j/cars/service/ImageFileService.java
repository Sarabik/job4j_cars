package ru.job4j.cars.service;

import ru.job4j.cars.dto.ImageFileDto;
import ru.job4j.cars.model.ImageFile;

import java.util.Optional;

public interface ImageFileService {

    ImageFile save(ImageFileDto imageFileDto);

    Optional<ImageFileDto> findById(int id);

    void delete(int id);

}
