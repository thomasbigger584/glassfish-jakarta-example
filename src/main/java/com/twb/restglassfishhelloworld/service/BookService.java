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

    @Inject
    private BookMapper mapper;

    public BookDTO createBook(CreateBookDTO dto) {
        Book book = mapper.createBookDtoToBook(dto);
        repository.save(book);
        return mapper.bookToBookDto(book);
    }
}
