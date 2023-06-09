package com.twb.restglassfishhelloworld.service;

import com.twb.restglassfishhelloworld.dto.BookDTO;
import com.twb.restglassfishhelloworld.dto.CreateBookDTO;
import com.twb.restglassfishhelloworld.entity.Book;
import com.twb.restglassfishhelloworld.mapper.BookMapper;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

@Service
public class BookService {
    @Inject
    private TransactionService transaction;

    public BookDTO createBook(CreateBookDTO dto) {
        Book book = BookMapper.INSTANCE.createBookDtoToBook(dto);

        transaction.runInTransaction(em -> {
            em.persist(book);
        });

        return BookMapper.INSTANCE.bookToBookDto(book);
    }
}
