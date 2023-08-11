package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.job4j.cars.model.EngineSize;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class HibernateEngineSizeRepositoryTest {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static EngineSizeRepository hibernateEngineSizeRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernateEngineSizeRepository = new HibernateEngineSizeRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void whenFindAllThenGetCollection() {
        Collection<EngineSize> result = hibernateEngineSizeRepository.findAll();
        assertThat(result).hasSize(19);
        List<Float> expected = Stream.iterate(1.2F, f  -> (float) Math.round((f + 0.1F) * 10) / 10).limit(19).toList();
        assertThat(result.stream().map(EngineSize::getSize).distinct()).containsExactlyElementsOf(expected);
    }
}