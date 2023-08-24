package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.job4j.cars.model.*;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HibernateUserRepositoryTest {

    private static StandardServiceRegistry registry;
    private static UserRepository hibernateUserRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernateUserRepository = new HibernateUserRepository(new CrudRepository(sessionFactory));
    }

    @AfterEach
    public void clear() {
        hibernateUserRepository.findAllOrderById()
                .forEach(p -> hibernateUserRepository.delete(p.getId()));
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void whenSaveThenFindItById() {
        User user1 = new User();
        user1.setEmail("login1");
        user1.setPassword("password1");
        user1.setPhoneNumber("123");
        hibernateUserRepository.save(user1);
        int id = user1.getId();

        User result = hibernateUserRepository.findById(id).get();
        assertThat(result).isEqualTo(user1);
    }

    @Test
    public void whenUpdateThenFindByIdUpdated() {
        User user1 = new User();
        user1.setEmail("login1");
        user1.setPassword("password1");
        user1.setPhoneNumber("123");
        hibernateUserRepository.save(user1);
        int id = user1.getId();

        User user2 = new User();
        user2.setId(user1.getId());
        user2.setEmail(user1.getEmail());
        user2.setPassword("password2");
        user2.setPhoneNumber(user1.getPhoneNumber());
        user2.setEmail(user1.getEmail());
        hibernateUserRepository.update(user2);

        String result = hibernateUserRepository.findById(id).get().getPassword();
        assertThat(result).isEqualTo("password2");
    }

    @Test
    public void whenDeleteThenFindByIdEmptyOptional() {
        User user1 = new User();
        user1.setEmail("login1");
        user1.setPassword("password1");
        user1.setPhoneNumber("123");
        hibernateUserRepository.save(user1);
        int id = user1.getId();
        hibernateUserRepository.delete(id);

        assertThat(hibernateUserRepository.findById(id)).isEmpty();
    }

    @Test
    public void whenFindAllThenGetCollection() {
        User user1 = new User();
        user1.setEmail("login1");
        user1.setPassword("password1");
        user1.setPhoneNumber("123");
        hibernateUserRepository.save(user1);

        Collection<User> result = hibernateUserRepository.findAllOrderById();
        assertThat(result).hasSize(1);
        assertThat(result).contains(user1);
    }

    @Test
    public void whenFindByLikeLoginThenGetCollection() {
        User user1 = new User();
        user1.setEmail("login1");
        user1.setPassword("password1");
        user1.setPhoneNumber("123");
        hibernateUserRepository.save(user1);

        User user2 = new User();
        user2.setEmail("lo");
        user2.setPassword("password2");
        user2.setPhoneNumber("123");
        user2.setEmail("mail1@mail.com");
        hibernateUserRepository.save(user2);

        Collection<User> result = hibernateUserRepository.findByLikeEmail("ogi");
        assertThat(result).contains(user1).doesNotContain(user2);
    }

    @Test
    public void whenFindByLoginThenGetIt() {
        User user1 = new User();
        user1.setEmail("login1");
        user1.setPassword("password1");
        user1.setPhoneNumber("123");
        hibernateUserRepository.save(user1);

        Optional<User> optionalUser = hibernateUserRepository.findByEmailAndPassword("login1", "password1");
        assertThat(optionalUser.get()).isEqualTo(user1);
    }

}