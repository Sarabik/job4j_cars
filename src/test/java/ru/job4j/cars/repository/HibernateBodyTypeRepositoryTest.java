package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.job4j.cars.model.BodyType;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

class HibernateBodyTypeRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static BodyTypeRepository hibernateBodyTypeRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernateBodyTypeRepository = new HibernateBodyTypeRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void whenFindAllThenGetCollection() {
        Collection<BodyType> result = hibernateBodyTypeRepository.findAll();
        assertThat(result).hasSize(9);
        assertThat(result.stream().map(BodyType::getName)).containsOnly(
                "Hatchback", "Sports Car", "SUV", "Sedan", "Convertible", "Coupe", "Station Wagon", "Mini Van", "Pickup"
        );
    }
}