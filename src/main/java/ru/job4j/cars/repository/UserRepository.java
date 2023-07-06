package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    public User create(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public List<User> findAllOrderById() {
        List<User> list = new ArrayList<>();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User order by id asc", User.class);
            list = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return list;
    }

    public Optional<User> findById(int userId) {
        Optional<User> optionalUser = Optional.empty();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User where id = :fId", User.class);
            query.setParameter("fId", userId);
            optionalUser = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return optionalUser;
    }

    public List<User> findByLikeLogin(String key) {
        List<User> list = new ArrayList<>();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("FROM User WHERE LOWER(login) LIKE LOWER(:fKey)", User.class);
            query.setParameter("fKey", "%" + key + "%");
            list = query.list();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return list;
    }

    public Optional<User> findByLogin(String login) {
        Optional<User> optionalUser = Optional.empty();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("FROM User WHERE login = :fLogin", User.class);
            query.setParameter("fLogin", login);
            optionalUser = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return optionalUser;
    }
}
