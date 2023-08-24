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

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HibernatePostRepositoryTest {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static PostRepository postRepository;
    private static ImageFileRepository imageFileRepository;
    private static CarRepository carRepository;
    private static PriceHistoryRepository priceHistoryRepository;
    private static UserRepository userRepository;
    private static int userId1;
    private static int userId2;
    private static int carId1;
    private static int carId2;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        postRepository = new HibernatePostRepository(new CrudRepository(sessionFactory), "directory", "noImage");
        imageFileRepository = new HibernateImageFileRepository(new CrudRepository(sessionFactory), "directory");
        carRepository = new HibernateCarRepository(new CrudRepository(sessionFactory));
        priceHistoryRepository = new HibernatePriceHistoryRepository(new CrudRepository(sessionFactory));
        userRepository = new HibernateUserRepository(new CrudRepository(sessionFactory));
        CarModelRepository carModelRepository = new HibernateCarModelRepository(new CrudRepository(sessionFactory));
        BodyTypeRepository bodyTypeRepository = new HibernateBodyTypeRepository(new CrudRepository(sessionFactory));
        FuelTypeRepository fuelTypeRepository = new HibernateFuelTypeRepository(new CrudRepository(sessionFactory));
        EngineSizeRepository engineSizeRepository = new HibernateEngineSizeRepository(new CrudRepository(sessionFactory));
        TransmissionRepository transmissionRepository = new HibernateTransmissionRepository(new CrudRepository(sessionFactory));
        CarColourRepository carColourRepository = new HibernateCarColourRepository(new CrudRepository(sessionFactory));

        User user1 = new User();
        user1.setEmail("email1");
        user1.setPassword("password1");
        user1.setPhoneNumber("1");
        userRepository.save(user1);
        userId1 = user1.getId();

        User user2 = new User();
        user2.setEmail("email2");
        user2.setPassword("password2");
        user2.setPhoneNumber("2");
        userRepository.save(user2);
        userId2 = user2.getId();

        Car car1 = new Car();
        car1.setCarModel(carModelRepository.findById(1).get());
        car1.setBodyType(bodyTypeRepository.findById(1).get());
        car1.setCarYear(1995);
        car1.setFuelType(fuelTypeRepository.findById(1).get());
        car1.setEngineSize(engineSizeRepository.findById(1).get());
        car1.setTransmission(transmissionRepository.findById(1).get());
        car1.setMileage(1L);
        car1.setCarColour(carColourRepository.findById(1).get());
        carRepository.save(car1);
        carId1 = car1.getId();

        Car car2 = new Car();
        car2.setCarModel(carModelRepository.findById(25).get());
        car2.setBodyType(bodyTypeRepository.findById(2).get());
        car2.setCarYear(2010);
        car2.setFuelType(fuelTypeRepository.findById(2).get());
        car2.setEngineSize(engineSizeRepository.findById(2).get());
        car2.setTransmission(transmissionRepository.findById(2).get());
        car2.setMileage(2L);
        car2.setCarColour(carColourRepository.findById(2).get());
        carRepository.save(car2);
        carId2 = car2.getId();
    }

    @AfterEach
    public void clear() {
        CrudRepository crudRepository = new CrudRepository(sessionFactory);
        crudRepository.query("FROM PriceHistory", PriceHistory.class)
                .forEach(p -> priceHistoryRepository.delete(p.getId()));
        postRepository.findAll()
                .forEach(p -> postRepository.delete(p.getId()));
        crudRepository.query("FROM ImageFile", ImageFile.class)
                .forEach(p -> imageFileRepository.delete(p.getId()));
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
        userRepository.delete(userId1);
        userRepository.delete(userId2);
        carRepository.delete(carId1);
        carRepository.delete(carId2);
    }

    @Test
    public void whenSaveThenFindItById() {
        Car car = carRepository.findById(carId1).get();
        User user = userRepository.findById(userId1).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(33000);
        post1.setCar(car);
        post1.setUser(user);
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(post1.getPrice());
        priceHistory.setCreated(post1.getCreated());
        post1.setPriceHistories(List.of(priceHistory));

        postRepository.save(post1);
        int postId1 = post1.getId();

        Post post2 = postRepository.findById(postId1).get();
        assertThat(post2.getDescription()).isEqualTo("description1");
    }

    @Test
    public void whenUpdateThenFindByIdUpdated() {
        Car car = carRepository.findById(carId1).get();
        User user = userRepository.findById(userId1).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car);
        post1.setUser(user);
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(post1.getPrice());
        priceHistory.setCreated(post1.getCreated());
        post1.setPriceHistories(List.of(priceHistory));
        postRepository.save(post1);
        int postId1 = post1.getId();

        Post post2 = new Post();
        post2.setId(postId1);
        post2.setDescription("description2");
        post2.setPrice(post1.getPrice());
        post2.setCar(post1.getCar());
        post2.setUser(post1.getUser());
        post2.setPriceHistories(post1.getPriceHistories());
        postRepository.update(post2);

        String result = postRepository.findById(postId1).get().getDescription();
        assertThat(result).isEqualTo("description2");
    }

    @Test
    public void whenDeleteThenFindByIdEmptyOptional() {
        Car car = carRepository.findById(carId1).get();
        User user = userRepository.findById(userId1).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car);
        post1.setUser(user);
        postRepository.save(post1);
        int postId1 = post1.getId();

        boolean idDeleted = postRepository.delete(postId1);

        assertThat(idDeleted).isTrue();
        assertThat(postRepository.findById(postId1)).isEmpty();
        assertThat(postRepository.delete(postId1)).isFalse();
    }

    @Test
    public void whenFindAllThenGetCollection() {
        Car car = carRepository.findById(carId1).get();
        User user1 = userRepository.findById(userId1).get();
        User user2 = userRepository.findById(userId2).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car);
        post1.setUser(user1);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setPrice(20000);
        post2.setCar(car);
        post2.setUser(user2);
        postRepository.save(post2);

        Collection<Post> result = postRepository.findAllNotSold();
        assertThat(result).hasSize(2);
        assertThat(result).contains(post1, post2);
    }

    @Test
    public void whenFindPostsForLast24HoursThenGetCollection() {
        Car car = carRepository.findById(carId1).get();
        User user1 = userRepository.findById(userId1).get();
        User user2 = userRepository.findById(userId2).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car);
        post1.setUser(user1);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setPrice(20000);
        post2.setCreated(LocalDateTime.now().minusDays(3));
        post2.setCar(car);
        post2.setUser(user2);
        postRepository.save(post2);

        Collection<Post> result = postRepository.findPostsForLast24Hours();
        assertThat(result).contains(post1).doesNotContain(post2);
    }

    @Test
    public void whenFindPostsWithPhotoThenGetCollection() {
        Car car = carRepository.findById(carId1).get();
        User user1 = userRepository.findById(userId1).get();
        User user2 = userRepository.findById(userId2).get();
        ImageFile imageFile = new ImageFile();
        imageFile.setPath("path");
        imageFile.setFileName("name");
        imageFileRepository.save(imageFile);

        Post post1 = new Post();
        post1.setImageFile(imageFile);
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car);
        post1.setUser(user1);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setPrice(20000);
        post2.setCar(car);
        post2.setUser(user2);
        postRepository.save(post2);

        Collection<Post> result = postRepository.findPostsWithPhoto();
        assertThat(result).contains(post1).doesNotContain(post2);
    }

    @Test
    public void whenFindPostsByMake() {
        Car car1 = carRepository.findById(carId1).get();
        Car car2 = carRepository.findById(carId2).get();
        User user = userRepository.findById(userId1).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car1);
        post1.setUser(user);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setPrice(20000);
        post2.setCar(car2);
        post2.setUser(user);
        postRepository.save(post2);

        Collection<Post> result = postRepository.findPostsByMake("Audi");
        assertThat(result).contains(post1).doesNotContain(post2);
    }

    @Test
    public void whenFindPostsByYearIntervalThenGetCollection() {
        Car car1 = carRepository.findById(carId1).get();
        Car car2 = carRepository.findById(carId2).get();
        User user = userRepository.findById(userId1).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car1);
        post1.setUser(user);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setPrice(20000);
        post2.setCar(car2);
        post2.setUser(user);
        postRepository.save(post2);

        Collection<Post> result = postRepository.findPostsByYearInterval(1980, 2000);
        assertThat(result).contains(post1).doesNotContain(post2);
    }

    @Test
    public void whenFindPostsByPriceIntervalThenGetCollection() {
        Car car1 = carRepository.findById(carId1).get();
        Car car2 = carRepository.findById(carId2).get();
        User user = userRepository.findById(userId1).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car1);
        post1.setUser(user);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setPrice(20000);
        post2.setCar(car2);
        post2.setUser(user);
        postRepository.save(post2);

        Collection<Post> result = postRepository.findPostsByPriceInterval(17000, 19000);
        assertThat(result).contains(post1).doesNotContain(post2);
    }

    @Test
    public void whenFindActivePostsByUserIdThenGetCollection() {
        Car car1 = carRepository.findById(carId1).get();
        Car car2 = carRepository.findById(carId2).get();
        User user = userRepository.findById(userId1).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car1);
        post1.setUser(user);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setPrice(20000);
        post2.setSold(true);
        post2.setCar(car2);
        post2.setUser(user);
        postRepository.save(post2);

        Collection<Post> result = postRepository.findAllActiveByUserId(userId1);
        assertThat(result).contains(post1).doesNotContain(post2);
    }

    @Test
    public void whenFindSoldPostsByUserIdThenGetCollection() {
        Car car1 = carRepository.findById(carId1).get();
        Car car2 = carRepository.findById(carId2).get();
        User user = userRepository.findById(userId1).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(18000);
        post1.setCar(car1);
        post1.setUser(user);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setDescription("description2");
        post2.setPrice(20000);
        post2.setSold(true);
        post2.setCar(car2);
        post2.setUser(user);
        postRepository.save(post2);

        Collection<Post> result = postRepository.findAllSoldByUserId(userId1);
        assertThat(result).contains(post2).doesNotContain(post1);
    }

    @Test
    public void whenChangePostStatusToSold() {
        Car car = carRepository.findById(carId1).get();
        User user = userRepository.findById(userId1).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(33000);
        post1.setCar(car);
        post1.setUser(user);
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(post1.getPrice());
        priceHistory.setCreated(post1.getCreated());
        post1.setPriceHistories(List.of(priceHistory));

        postRepository.save(post1);
        int postId1 = post1.getId();
        postRepository.movePostToSold(postId1);

        assertThat(postRepository.findById(postId1).get().isSold()).isTrue();
    }

    @Test
    public void whenChangeNewDate() {
        Car car = carRepository.findById(carId1).get();
        User user = userRepository.findById(userId1).get();

        Post post1 = new Post();
        post1.setDescription("description1");
        post1.setPrice(33000);
        post1.setCar(car);
        post1.setUser(user);
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(post1.getPrice());
        priceHistory.setCreated(post1.getCreated());
        post1.setPriceHistories(List.of(priceHistory));

        postRepository.save(post1);
        LocalDateTime date = post1.getCreated();
        int postId1 = post1.getId();
        postRepository.updateDate(postId1);

        assertThat(postRepository.findById(postId1).get().getCreated()).isAfter(date);
    }

}