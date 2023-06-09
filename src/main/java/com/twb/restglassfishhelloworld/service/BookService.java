package com.twb.restglassfishhelloworld.service;

import com.twb.restglassfishhelloworld.dto.BookDTO;
import com.twb.restglassfishhelloworld.dto.CreateBookDTO;
import com.twb.restglassfishhelloworld.entity.Book;
import com.twb.restglassfishhelloworld.exception.BookException;
import com.twb.restglassfishhelloworld.mapper.BookMapper;
import com.twb.restglassfishhelloworld.repository.BookRepository;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

import java.util.List;

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

    public BookDTO getBookById(Long id) {
        Book book = repository.findById(id);
        if (book == null) {
            throw new BookException("Book not found: " + id);
        }
        return mapper.bookToBookDto(book);
    }

    public List<BookDTO> getBooks() {
        List<Book> books = repository.findAll();
        return books.stream().map(mapper::bookToBookDto).toList();
    }

    public void deleteBook(Long id) {
        repository.delete(id);
    }
}
