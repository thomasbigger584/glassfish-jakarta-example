package com.twb.restglassfishhelloworld.service;

import com.twb.restglassfishhelloworld.entity.Book;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

@Service
public class BookService {

    @Inject
    private TransactionService transaction;


    public Book createBook() {
        Book book = new Book();
        book.setName("Book Name");

        transaction.runInTransaction(em -> {
            em.persist(book);
        });

        return book;
    }
}
