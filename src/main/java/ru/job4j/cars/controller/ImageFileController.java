package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.cars.dto.ImageFileDto;
import ru.job4j.cars.service.ImageFileService;

import java.util.Optional;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
public class ImageFileController {

    private final ImageFileService imageFileService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<ImageFileDto> contentOptional = imageFileService.findById(id);
        if (contentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contentOptional.get().getContent());
    }

}
