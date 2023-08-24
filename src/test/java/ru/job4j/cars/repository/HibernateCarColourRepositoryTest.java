package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.job4j.cars.model.CarColour;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

class HibernateCarColourRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static CarColourRepository hibernateCarColourRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernateCarColourRepository = new HibernateCarColourRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void whenFindAllThenGetCollection() {
        Collection<CarColour> result = hibernateCarColourRepository.findAll();
        assertThat(result).hasSize(13);
        assertThat(result.stream().map(CarColour::getName)).containsOnly(
                "beige", "black", "multicolor", "pink", "brown", "yellow",
                "green", "purple", "blue", "orange", "white", "red", "grey"
        );
    }
}