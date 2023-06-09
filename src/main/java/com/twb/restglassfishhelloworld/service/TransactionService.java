package com.twb.restglassfishhelloworld.service;

import com.twb.restglassfishhelloworld.config.LiquibaseStartupHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.jvnet.hk2.annotations.Service;

import java.util.function.Consumer;

@Service
public class TransactionService {

    public void runInTransaction(Consumer<EntityManager> consumer) {
        try (EntityManagerFactory factory = Persistence
                .createEntityManagerFactory(LiquibaseStartupHandler.PERSISTENCE_UNIT_NAME);
             EntityManager entityManager = factory.createEntityManager()) {

            entityManager.getTransaction().begin();
            consumer.accept(entityManager);
            entityManager.getTransaction().commit();
        }
    }
}
