package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.User;

public class UserUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            HibernateUserRepository userRepository = new HibernateUserRepository(new CrudRepository(sf));
            User user = new User();
            user.setEmail("admin");
            user.setPassword("admin");
            userRepository.save(user);
            userRepository.findAllOrderById()
                    .forEach(System.out::println);
            userRepository.findByLikeEmail("i")
                    .forEach(System.out::println);
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            userRepository.findByEmailAndPassword("admin", "admin")
                    .ifPresent(System.out::println);
            user.setPassword("password");
            userRepository.update(user);
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            userRepository.delete(user.getId());
            userRepository.findAllOrderById()
                    .forEach(System.out::println);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}