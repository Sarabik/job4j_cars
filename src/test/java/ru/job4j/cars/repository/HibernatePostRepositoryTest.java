package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HibernatePostRepositoryTest {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static PostRepository hibernatePostRepository;
    private static ImageFileRepository hibernateImageFileRepository;
    private static UserRepository hibernateUserRepository;
    private static CarRepository hibernateCarRepository;
    private static PriceHistoryRepository hibernatePriceHistoryRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernatePostRepository = new HibernatePostRepository(new CrudRepository(sessionFactory));
        hibernateImageFileRepository = new HibernateImageFileRepository(new CrudRepository(sessionFactory));
        hibernateUserRepository = new HibernateUserRepository(new CrudRepository(sessionFactory));
        hibernateCarRepository = new HibernateCarRepository(new CrudRepository(sessionFactory));
        hibernatePriceHistoryRepository = new HibernatePriceHistoryRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void whenSaveThenFindItById() {
        Car car = hibernateCarRepository.findById(1).get();
        User user = hibernateUserRepository.findById(3).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(33000);
        post1.setCar(car);
        post1.setUser(user);
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(post1.getPrice());
        priceHistory.setCreated(post1.getCreated());
        post1.setPriceHistories(List.of(priceHistory));

        hibernatePostRepository.save(post1);
        int postId1 = post1.getId();

        Post post2 = hibernatePostRepository.findById(postId1).get();
        assertThat(post2.getDescription()).isEqualTo("description1");

        hibernatePriceHistoryRepository.delete(priceHistory.getId());
        hibernatePostRepository.delete(postId1);
    }

    @Test
    public void whenUpdateThenFindByIdUpdated() {
        Car car = hibernateCarRepository.findById(1).get();
        User user = hibernateUserRepository.findById(3).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car);
        post1.setUser(user);
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(post1.getPrice());
        priceHistory.setCreated(post1.getCreated());
        post1.setPriceHistories(List.of(priceHistory));
        hibernatePostRepository.save(post1);
        int postId1 = post1.getId();

        Post post2 = new Post();
        post2.setId(postId1);
        post2.setDescription("description2");
        post2.setPrice(post1.getPrice());
        post2.setCar(post1.getCar());
        post2.setUser(post1.getUser());
        post2.setPriceHistories(post1.getPriceHistories());
        hibernatePostRepository.update(post2);
        System.out.println("**************" + post2.getId() + post2.getDescription() + post2.getPriceHistories());

        String result = hibernatePostRepository.findById(postId1).get().getDescription();
        assertThat(result).isEqualTo("description2");
        hibernatePriceHistoryRepository.delete(priceHistory.getId());
        hibernatePostRepository.delete(postId1);
    }

    @Test
    public void whenDeleteThenFindByIdEmptyOptional() {
        Car car = hibernateCarRepository.findById(1).get();
        User user = hibernateUserRepository.findById(3).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car);
        post1.setUser(user);
        hibernatePostRepository.save(post1);
        int postId1 = post1.getId();

        hibernatePostRepository.delete(postId1);

        assertThat(hibernatePostRepository.findById(postId1)).isEmpty();
    }

    @Test
    public void whenFindAllThenGetCollection() {
        Car car = hibernateCarRepository.findById(1).get();
        User user1 = hibernateUserRepository.findById(3).get();
        User user2 = hibernateUserRepository.findById(2).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car);
        post1.setUser(user1);
        hibernatePostRepository.save(post1);
        int postId1 = post1.getId();

        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setPrice(20000);
        post2.setCar(car);
        post2.setUser(user2);
        hibernatePostRepository.save(post2);
        int postId2 = post2.getId();

        Collection<Post> result = hibernatePostRepository.findAll();
        assertThat(result).hasSizeGreaterThanOrEqualTo(2);
        assertThat(result).contains(post1, post2);
        hibernatePostRepository.delete(postId1);
        hibernatePostRepository.delete(postId2);
    }

    @Test
    public void whenFindPostsForLast24HoursThenGetCollection() {
        Car car = hibernateCarRepository.findById(1).get();
        User user1 = hibernateUserRepository.findById(3).get();
        User user2 = hibernateUserRepository.findById(2).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car);
        post1.setUser(user1);
        hibernatePostRepository.save(post1);
        int postId1 = post1.getId();

        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setPrice(20000);
        post2.setCreated(LocalDateTime.now().minusDays(3));
        post2.setCar(car);
        post2.setUser(user2);
        hibernatePostRepository.save(post2);
        int postId2 = post2.getId();

        Collection<Post> result = hibernatePostRepository.findPostsForLast24Hours();
        assertThat(result).contains(post1).doesNotContain(post2);
        hibernatePostRepository.delete(postId1);
        hibernatePostRepository.delete(postId2);
    }

    @Test
    public void whenFindPostsWithPhotoThenGetCollection() {
        Car car = hibernateCarRepository.findById(1).get();
        User user1 = hibernateUserRepository.findById(3).get();
        User user2 = hibernateUserRepository.findById(2).get();
        ImageFile imageFile = new ImageFile();
        imageFile.setPath("path");
        imageFile.setFileName("name");
        hibernateImageFileRepository.save(imageFile);
        int imageId = imageFile.getId();

        Post post1 = new Post();
        post1.setImageFile(imageFile);
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car);
        post1.setUser(user1);
        hibernatePostRepository.save(post1);
        int postId1 = post1.getId();

        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setPrice(20000);
        post2.setCar(car);
        post2.setUser(user2);
        hibernatePostRepository.save(post2);
        int postId2 = post2.getId();

        Collection<Post> result = hibernatePostRepository.findPostsWithPhoto();
        assertThat(result).contains(post1).doesNotContain(post2);
        hibernatePostRepository.delete(postId1);
        hibernatePostRepository.delete(postId2);
        hibernateImageFileRepository.delete(imageId);
    }

    @Test
    public void whenFindPostsByMake() {
        Car car1 = hibernateCarRepository.findById(1).get();
        Car car2 = hibernateCarRepository.findById(2).get();
        User user = hibernateUserRepository.findById(3).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car1);
        post1.setUser(user);
        hibernatePostRepository.save(post1);
        int postId1 = post1.getId();

        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setPrice(20000);
        post2.setCar(car2);
        post2.setUser(user);
        hibernatePostRepository.save(post2);
        int postId2 = post2.getId();

        Collection<Post> result = hibernatePostRepository.findPostsByMake("Volkswagen");
        assertThat(result).contains(post1).doesNotContain(post2);
        hibernatePostRepository.delete(postId1);
        hibernatePostRepository.delete(postId2);
    }
}