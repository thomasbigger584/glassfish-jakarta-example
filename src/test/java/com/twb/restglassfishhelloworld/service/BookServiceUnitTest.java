package com.twb.restglassfishhelloworld.service;

import com.twb.restglassfishhelloworld.dto.BookDTO;
import com.twb.restglassfishhelloworld.dto.CreateBookDTO;
import com.twb.restglassfishhelloworld.entity.Book;
import com.twb.restglassfishhelloworld.mapper.BookMapper;
import com.twb.restglassfishhelloworld.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookServiceUnitTest {
    private static final String TEST_BOOK_NAME = "test-book-name";
    private static final long TEST_BOOK_ID_1 = 1L;

    @Mock
    private BookRepository repository;

    @Mock
    private BookMapper mapper;

    @InjectMocks
    private BookService target;

    @Test
    void testCreateBook() {
        CreateBookDTO createDto = new CreateBookDTO();
        createDto.setName(TEST_BOOK_NAME);

        Book book = new Book();
        book.setId(TEST_BOOK_ID_1);
        book.setName(TEST_BOOK_NAME);

        BookDTO dto = new BookDTO();
        dto.setId(TEST_BOOK_ID_1);
        dto.setName(TEST_BOOK_NAME);

        when(mapper.createBookDtoToBook(createDto)).thenReturn(book);
        when(mapper.bookToBookDto(book)).thenReturn(dto);

        BookDTO createdBook = target.createBook(createDto);

        verify(repository).save(book);

        assertEquals(TEST_BOOK_ID_1, createdBook.getId());
        assertEquals(createDto.getName(), createdBook.getName());
    }
}