package com.twb.restglassfishhelloworld.repository;


import com.twb.restglassfishhelloworld.service.TransactionService;
import jakarta.inject.Inject;

public abstract class AbstractRepository<T> {

    @Inject
    private TransactionService transactionService;

    public T save(T entity) {
        transactionService.runInTransaction(em -> {
            em.persist(entity);
        });
        return entity;
    }
}
