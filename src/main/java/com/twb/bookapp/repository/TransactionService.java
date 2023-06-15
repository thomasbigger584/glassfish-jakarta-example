package com.twb.bookapp.repository;

import com.twb.bookapp.config.LiquibaseStartupHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.jvnet.hk2.annotations.Service;

import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class TransactionService {

    public void runInTransaction(Consumer<EntityManager> consumer) {
        try (EntityManagerFactory factory = Persistence
                .createEntityManagerFactory(LiquibaseStartupHandler.PERSISTENCE_UNIT_NAME);
             EntityManager entityManager = factory.createEntityManager()) {

            EntityTransaction transaction = entityManager.getTransaction();

            try {

                transaction.begin();
                consumer.accept(entityManager);
                transaction.commit();

            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    public <T> T runInTransaction(Function<EntityManager, T> function) {
        try (EntityManagerFactory factory = Persistence
                .createEntityManagerFactory(LiquibaseStartupHandler.PERSISTENCE_UNIT_NAME);
             EntityManager entityManager = factory.createEntityManager()) {

            EntityTransaction transaction = entityManager.getTransaction();

            try {

                transaction.begin();
                T result = function.apply(entityManager);
                transaction.commit();

                return result;

            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }
}
