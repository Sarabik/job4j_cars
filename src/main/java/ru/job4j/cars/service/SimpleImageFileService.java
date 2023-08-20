package ru.job4j.cars.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.ImageFileDto;
import ru.job4j.cars.model.ImageFile;
import ru.job4j.cars.repository.ImageFileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimpleImageFileService implements ImageFileService {

    private final ImageFileRepository imageFileRepository;

    private final String storageDirectory;

    private final String noImage;

    public SimpleImageFileService(ImageFileRepository imageFileRepository,
                                  @Value("${file.directory}") String storageDirectory,
                                  @Value("${file.default.name}") String noImage) {
        this.imageFileRepository = imageFileRepository;
        this.storageDirectory = storageDirectory;
        this.noImage = noImage;
    }

    @Override
    public ImageFile save(ImageFileDto imageFileDto) {
        ImageFile imageFile = new ImageFile();
        if (imageFileDto != null) {
            String path = getNewFilePath(imageFileDto.getName());
            writeFileBytes(path, imageFileDto.getContent());
            imageFile.setFileName(imageFileDto.getName());
            imageFile.setPath(path);
            imageFileRepository.save(imageFile);
        } else {
            imageFile.setPath(storageDirectory + java.io.File.separator + noImage);
            imageFile.setFileName(noImage);
        }
        return imageFile;
    }

    private String getNewFilePath(String fileName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + fileName;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ImageFileDto> findById(int id) {
        Optional<ImageFile> opt = imageFileRepository.findById(id);
        if (opt.isEmpty()) {
            return Optional.empty();
        }
        byte[] content = readFileAsBytes(opt.get().getPath());
        return Optional.of(new ImageFileDto(opt.get().getFileName(), content));
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        imageFileRepository.delete(id);
    }
}
