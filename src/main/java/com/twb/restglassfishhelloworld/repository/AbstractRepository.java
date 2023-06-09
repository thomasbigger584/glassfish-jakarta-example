package com.twb.restglassfishhelloworld.repository;


import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class AbstractRepository<T> {
    private final Class<T> clazz;

    @Inject
    private TransactionService transactionService;

    @SuppressWarnings("unchecked")
    protected AbstractRepository() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;
        Type[] typeArgs = paramType.getActualTypeArguments();
        this.clazz = (Class<T>) typeArgs[0];
    }

    public T save(T entity) {
        transactionService.runInTransaction(em -> {
            em.persist(entity);
        });
        return entity;
    }

    public T findById(Long id) {
        return transactionService.runInTransaction(em -> {
            return em.find(clazz, id);
        });
    }

    public List<T> findAll() {
        return transactionService.runInTransaction(em -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(clazz);
            Root<T> root = query.from(clazz);
            query.select(root);
            return em.createQuery(query).getResultList();
        });
    }

    public void delete(Long id) {
        transactionService.runInTransaction(em -> {
            T result = em.find(clazz, id);
            if (result != null) {
                em.remove(result);
            }
        });
    }
}
