package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.job4j.cars.model.*;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HibernateUserRepositoryTest {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static UserRepository hibernateUserRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernateUserRepository = new HibernateUserRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void whenSaveThenFindItById() {
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        hibernateUserRepository.save(user1);
        int id = user1.getId();

        User result = hibernateUserRepository.findById(id).get();
        assertThat(result).isEqualTo(user1);
        hibernateUserRepository.delete(id);
    }

    @Test
    public void whenUpdateThenFindByIdUpdated() {
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        hibernateUserRepository.save(user1);
        int id = user1.getId();

        User user2 = new User();
        user2.setId(user1.getId());
        user2.setLogin(user1.getLogin());
        user2.setPassword("password2");
        hibernateUserRepository.update(user2);

        String result = hibernateUserRepository.findById(id).get().getPassword();
        assertThat(result).isEqualTo("password2");
        hibernateUserRepository.delete(id);
    }

    @Test
    public void whenDeleteThenFindByIdEmptyOptional() {
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        hibernateUserRepository.save(user1);
        int id = user1.getId();
        hibernateUserRepository.delete(id);

        assertThat(hibernateUserRepository.findById(id)).isEmpty();
    }

    @Test
    public void whenFindAllThenGetCollection() {
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        hibernateUserRepository.save(user1);
        int id = user1.getId();

        Collection<User> result = hibernateUserRepository.findAllOrderById();
        assertThat(result).hasSizeGreaterThanOrEqualTo(1);
        assertThat(result).contains(user1);
        hibernateUserRepository.delete(id);
    }

    @Test
    public void whenFindByLikeLoginThenGetCollection() {
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        hibernateUserRepository.save(user1);
        int id1 = user1.getId();

        User user2 = new User();
        user2.setLogin("lo");
        user2.setPassword("password2");
        hibernateUserRepository.save(user2);
        int id2 = user2.getId();

        Collection<User> result = hibernateUserRepository.findByLikeLogin("ogi");
        assertThat(result).contains(user1).doesNotContain(user2);
        hibernateUserRepository.delete(id1);
        hibernateUserRepository.delete(id2);
    }

    @Test
    public void whenFindByLoginThenGetIt() {
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        hibernateUserRepository.save(user1);
        int id = user1.getId();

        Optional<User> optionalUser = hibernateUserRepository.findByLogin("login1");
        assertThat(optionalUser.get()).isEqualTo(user1);
        hibernateUserRepository.delete(id);
    }
}