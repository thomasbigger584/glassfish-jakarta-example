package com.twb.bookapp.resource;

import com.twb.bookapp.dto.BookDTO;
import com.twb.bookapp.dto.CreateBookDTO;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookResourceIntegrationTest extends AbstractIntegrationTest {
    private static final String TEST_BOOK_NAME = "test-book-name-%d";

    private static BookDTO createBook() throws Exception {
        CreateBookDTO createBookDto = new CreateBookDTO();
        createBookDto.setName(String.format(TEST_BOOK_NAME, RandomGenerator.getDefault().nextInt()));

        ApiHttpResponse<BookDTO> apiHttpResponse = post(BookDTO.class, createBookDto, "/book");

        HttpResponse<String> httpResponse = apiHttpResponse.getHttpResponse();
        assertEquals(HttpStatus.CREATED_201.getStatusCode(), httpResponse.statusCode());

        BookDTO createdBookDto = apiHttpResponse.getResultBody();
        assertTrue(createdBookDto.getId() > 0);
        assertEquals(createBookDto.getName(), createdBookDto.getName());

        return createdBookDto;
    }

    private static BookDTO fetchBook(BookDTO book) throws Exception {
        ApiHttpResponse<BookDTO> apiHttpResponse = get(BookDTO.class, "/book/" + book.getId());

        HttpResponse<String> httpResponse = apiHttpResponse.getHttpResponse();
        assertEquals(HttpStatus.OK_200.getStatusCode(), httpResponse.statusCode());

        BookDTO fetchedBookDto = apiHttpResponse.getResultBody();
        assertEquals(book, fetchedBookDto);

        return fetchedBookDto;
    }

    private static BookDTO[] fetchAllBooks() throws Exception {
        ApiHttpResponse<BookDTO[]> apiHttpResponse = get(BookDTO[].class, "/book");

        HttpResponse<String> httpResponse = apiHttpResponse.getHttpResponse();
        assertEquals(HttpStatus.OK_200.getStatusCode(), httpResponse.statusCode());

        return apiHttpResponse.getResultBody();
    }

    private static void deleteBook(BookDTO book) throws Exception {
        ApiHttpResponse<?> apiHttpResponse = delete("/book/" + book.getId());

        HttpResponse<String> httpResponse = apiHttpResponse.getHttpResponse();
        assertEquals(HttpStatus.NO_CONTENT_204.getStatusCode(), httpResponse.statusCode());
    }

    @Test
    public void testCreateBook_ThenFetchEachBook_ThenFetchAllBooks() throws Exception {
        List<BookDTO> books = new ArrayList<>();
        for (int index = 0; index < 3; index++) {
            books.add(createBook());
        }

        for (BookDTO book : books) {
            fetchBook(book);
        }

        List<BookDTO> allBooks = Arrays.asList(fetchAllBooks());
        assertTrue(allBooks.containsAll(books));
    }

    @Test
    public void testDeleteAllBooks() throws Exception {
        createBook();
        BookDTO[] allBooksBeforeDelete = fetchAllBooks();
        assertTrue(allBooksBeforeDelete.length > 0);

        for (BookDTO book : allBooksBeforeDelete) {
            deleteBook(book);
        }

        BookDTO[] allBooksAfterDelete = fetchAllBooks();
        assertEquals(0, allBooksAfterDelete.length);
    }
}
