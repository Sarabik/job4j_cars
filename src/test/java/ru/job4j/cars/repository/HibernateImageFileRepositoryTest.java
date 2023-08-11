package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.job4j.cars.model.ImageFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HibernateImageFileRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static ImageFileRepository hibernateImageFileRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernateImageFileRepository = new HibernateImageFileRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void whenSaveImageThenFindItById() {
        ImageFile imageFile1 = new ImageFile();
        imageFile1.setPath("path");
        imageFile1.setFileName("name");
        hibernateImageFileRepository.save(imageFile1);
        int id = imageFile1.getId();

        Optional<ImageFile> imageFile2 = hibernateImageFileRepository.findById(id);
        assertThat(imageFile2.get().getPath()).isEqualTo("path");
        assertThat(imageFile2.get().getFileName()).isEqualTo("name");
        hibernateImageFileRepository.delete(id);
    }

    @Test
    public void whenUpdateImageThenFindUpdated() {
        ImageFile imageFile1 = new ImageFile();
        imageFile1.setPath("path");
        imageFile1.setFileName("name");
        hibernateImageFileRepository.save(imageFile1);
        int id = imageFile1.getId();

        ImageFile imageFile2 = new ImageFile();
        imageFile2.setId(id);
        imageFile2.setPath("path2");
        imageFile2.setFileName("name2");
        hibernateImageFileRepository.update(imageFile2);

        Optional<ImageFile> result = hibernateImageFileRepository.findById(id);
        assertThat(result.get().getPath()).isEqualTo("path2");
        assertThat(result.get().getFileName()).isEqualTo("name2");
        hibernateImageFileRepository.delete(id);
    }

    @Test
    public void whenDeleteImageThenGetEmptyOptional() {
        ImageFile imageFile1 = new ImageFile();
        imageFile1.setPath("path");
        imageFile1.setFileName("name");
        hibernateImageFileRepository.save(imageFile1);
        int id = imageFile1.getId();
        hibernateImageFileRepository.delete(id);
        Optional<ImageFile> optionalImageFile = hibernateImageFileRepository.findById(id);
        assertThat(optionalImageFile).isEmpty();
    }
}