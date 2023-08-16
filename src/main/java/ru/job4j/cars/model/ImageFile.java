package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.job4j.cars.dto.ImageFileDto;

import javax.persistence.*;

@Entity
@Table(name = "image_files")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ImageFile {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String path;

    @Column(name = "file_name")
    private String fileName;

    public ImageFile(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }
}
