package com.twb.restglassfishhelloworld.service;

import com.twb.restglassfishhelloworld.dto.BookDTO;
import com.twb.restglassfishhelloworld.dto.CreateBookDTO;
import com.twb.restglassfishhelloworld.entity.Book;
import com.twb.restglassfishhelloworld.exception.BookException;
import com.twb.restglassfishhelloworld.mapper.BookMapper;
import com.twb.restglassfishhelloworld.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookServiceUnitTest {
    private static final String TEST_BOOK_NAME = "test-book-name";
    private static final long TEST_BOOK_ID_1 = 1L;
    private static final long TEST_BOOK_ID_2 = 2L;

    @Mock
    private BookRepository repository;

    @Mock
    private BookMapper mapper;

    @InjectMocks
    private BookService target;

    private static CreateBookDTO buildCreateBookDto() {
        CreateBookDTO createBookDto = new CreateBookDTO();
        createBookDto.setName(TEST_BOOK_NAME);
        return createBookDto;
    }

    private static Book buildBook() {
        Book book = new Book();
        book.setId(TEST_BOOK_ID_1);
        book.setName(TEST_BOOK_NAME);
        return book;
    }

    private static BookDTO buildBookDto() {
        BookDTO bookDto = new BookDTO();
        bookDto.setId(TEST_BOOK_ID_1);
        bookDto.setName(TEST_BOOK_NAME);
        return bookDto;
    }

    @Test
    void testCreateBook() {
        CreateBookDTO createBookDto = buildCreateBookDto();
        Book book = buildBook();
        BookDTO bookDto = buildBookDto();

        when(mapper.createBookDtoToBook(createBookDto)).thenReturn(book);
        when(mapper.bookToBookDto(book)).thenReturn(bookDto);

        BookDTO createdBook = target.createBook(createBookDto);

        verify(repository).save(book);

        assertEquals(TEST_BOOK_ID_1, createdBook.getId());
        assertEquals(createBookDto.getName(), createdBook.getName());
    }

    @Test
    void testGetBookById() {
        Book book = buildBook();
        BookDTO bookDto = buildBookDto();

        when(repository.findById(TEST_BOOK_ID_1)).thenReturn(Optional.of(book));
        when(mapper.bookToBookDto(book)).thenReturn(bookDto);

        BookDTO fetchedBook = target.getBookById(TEST_BOOK_ID_1);

        assertEquals(TEST_BOOK_ID_1, fetchedBook.getId());
        assertEquals(book.getName(), fetchedBook.getName());
    }

    @Test
    void testGetBookById_NotFound() {
        when(repository.findById(TEST_BOOK_ID_1)).thenReturn(Optional.empty());
        BookException exception = assertThrows(BookException.class, () -> {
            target.getBookById(TEST_BOOK_ID_1);
        });
        assertEquals("Book not found: " + TEST_BOOK_ID_1, exception.getMessage());
    }

    @Test
    void testGetBooks() {
        Book book1 = buildBook();
        Book book2 = buildBook();
        book2.setId(TEST_BOOK_ID_2);

        BookDTO bookDto1 = buildBookDto();
        BookDTO bookDto2 = buildBookDto();
        bookDto2.setId(TEST_BOOK_ID_2);

        List<Book> booksList = Arrays.asList(book1, book2);
        when(repository.findAll()).thenReturn(booksList);
        when(mapper.bookToBookDto(book1)).thenReturn(bookDto1);
        when(mapper.bookToBookDto(book2)).thenReturn(bookDto2);

        List<BookDTO> fetchedBooksList = target.getBooks();

        assertEquals(booksList.size(), fetchedBooksList.size());

        BookDTO fetchedBook1 = fetchedBooksList.get(0);
        assertEquals(TEST_BOOK_ID_1, fetchedBook1.getId());
        assertEquals(book1.getName(), fetchedBook1.getName());

        BookDTO fetchedBook2 = fetchedBooksList.get(1);
        assertEquals(TEST_BOOK_ID_2, fetchedBook2.getId());
        assertEquals(book2.getName(), fetchedBook2.getName());
    }

    @Test
    void testDeleteBook() {
        target.deleteBook(TEST_BOOK_ID_1);
        verify(repository).delete(TEST_BOOK_ID_1);
    }
}