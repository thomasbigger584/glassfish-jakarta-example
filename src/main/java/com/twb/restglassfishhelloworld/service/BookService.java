package com.twb.restglassfishhelloworld.service;

import com.twb.restglassfishhelloworld.dto.BookDTO;
import com.twb.restglassfishhelloworld.dto.CreateBookDTO;
import com.twb.restglassfishhelloworld.entity.Book;
import com.twb.restglassfishhelloworld.mapper.BookMapper;
import com.twb.restglassfishhelloworld.repository.BookRepository;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

@Service
public class BookService {
    @Inject
    private BookRepository repository;

    public BookDTO createBook(CreateBookDTO dto) {
        Book book = BookMapper.INSTANCE.createBookDtoToBook(dto);
        repository.save(book);
        return BookMapper.INSTANCE.bookToBookDto(book);
    }
}
