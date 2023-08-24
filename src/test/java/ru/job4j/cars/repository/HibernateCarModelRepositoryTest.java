package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.job4j.cars.model.CarModel;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

class HibernateCarModelRepositoryTest {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static CarModelRepository hibernateCarModelRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernateCarModelRepository = new HibernateCarModelRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void whenFindAllThenGetCollection() {
        Collection<CarModel> result = hibernateCarModelRepository.findAll();
        assertThat(result).hasSize(40);
        assertThat(result.stream().map(CarModel::getMake).distinct()).containsOnly(
                "Audi", "BMW", "Mercedes", "Volkswagen", "Other"
        );
    }
}