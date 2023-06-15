package com.twb.bookapp.service;

import com.twb.bookapp.dto.BookDTO;
import com.twb.bookapp.dto.CreateBookDTO;
import com.twb.bookapp.entity.Book;
import com.twb.bookapp.exception.BookException;
import com.twb.bookapp.mapper.BookMapper;
import com.twb.bookapp.repository.BookRepository;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Book> book = repository.findById(id);
        if (book.isEmpty()) {
            throw new BookException("Book not found: " + id);
        }
        return mapper.bookToBookDto(book.get());
    }

    public List<BookDTO> getBooks() {
        List<Book> books = repository.findAll();
        return books.stream().map(mapper::bookToBookDto).toList();
    }

    public void deleteBook(Long id) {
        repository.delete(id);
    }
}
