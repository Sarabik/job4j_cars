package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.job4j.cars.model.Transmission;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

class HibernateTransmissionRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static TransmissionRepository hibernateTransmissionRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernateTransmissionRepository = new HibernateTransmissionRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void whenFindAllThenGetCollection() {
        Collection<Transmission> result = hibernateTransmissionRepository.findAll();
        assertThat(result).hasSize(2);
        assertThat(result.stream().map(Transmission::getName)).containsOnly(
                "Automatic", "Manual"
        );
    }
}