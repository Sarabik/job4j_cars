package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.job4j.cars.model.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HibernateCarRepositoryTest {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static CarRepository hibernateCarRepository;
    private static CarModelRepository hibernateCarModelRepository;
    private static BodyTypeRepository hibernateBodyTypeRepository;
    private static FuelTypeRepository hibernateFuelTypeRepository;
    private static EngineSizeRepository hibernateEngineSizeRepository;
    private static TransmissionRepository hibernateTransmissionRepository;
    private static CarColourRepository hibernateCarColourRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        hibernateCarRepository = new HibernateCarRepository(new CrudRepository(sessionFactory));
        hibernateCarModelRepository = new HibernateCarModelRepository(new CrudRepository(sessionFactory));
        hibernateBodyTypeRepository = new HibernateBodyTypeRepository(new CrudRepository(sessionFactory));
        hibernateFuelTypeRepository = new HibernateFuelTypeRepository(new CrudRepository(sessionFactory));
        hibernateEngineSizeRepository = new HibernateEngineSizeRepository(new CrudRepository(sessionFactory));
        hibernateTransmissionRepository = new HibernateTransmissionRepository(new CrudRepository(sessionFactory));
        hibernateCarColourRepository = new HibernateCarColourRepository(new CrudRepository(sessionFactory));
    }

    @AfterAll
    public static void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    public void whenSaveThenFindItById() {
        Car car = new Car();
        car.setCarModel(hibernateCarModelRepository.findById(1).get());
        car.setBodyType(hibernateBodyTypeRepository.findById(1).get());
        car.setCarYear(2010);
        car.setFuelType(hibernateFuelTypeRepository.findById(1).get());
        car.setEngineSize(hibernateEngineSizeRepository.findById(1).get());
        car.setTransmission(hibernateTransmissionRepository.findById(1).get());
        car.setMileage(15000);
        car.setCarColour(hibernateCarColourRepository.findById(1).get());
        car.setNumber("number");
        car.setVin("vin");
        hibernateCarRepository.save(car);
        int id = car.getId();

        Optional<Car> optionalCar = hibernateCarRepository.findById(id);
        assertThat(optionalCar.get()).isEqualTo(car);
        hibernateCarRepository.delete(id);
    }

}