package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
@AllArgsConstructor
public class CrudRepository {
    private final SessionFactory sf;

    public <T> boolean ifChanged(T t) {
        Function<Session, Boolean> command = session -> {
            session.update(t);
            return true;
        };
        return tx(command);
    }

    public boolean ifChanged(String query, Map<String, Object> args) {
        Function<Session, Boolean> command = session -> {
            var q = session.createQuery(query);
            for (Map.Entry<String, Object> entry : args.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
            return q.executeUpdate() > 0;
        };
        return tx(command);
    }

    public <T> boolean ifSaved(T t) {
        Function<Session, Boolean> command = session -> {
            session.persist(t);
            return true;
        };
        return tx(command);
    }



    public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            Query<T> sq = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.uniqueResultOptional();
        };
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> session
                .createQuery(query, cl)
                .list();
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, List<T>> command = session -> {
            Query<T> sq = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.list();
        };
        return tx(command);
    }

    public <T> T tx(Function<Session, T> command) {
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}
