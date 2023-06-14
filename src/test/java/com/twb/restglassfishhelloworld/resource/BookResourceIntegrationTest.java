package com.twb.restglassfishhelloworld.resource;

import com.twb.restglassfishhelloworld.dto.BookDTO;
import com.twb.restglassfishhelloworld.dto.CreateBookDTO;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookResourceIntegrationTest extends AbstractGlassfishIntegrationTest {
    private static final String TEST_BOOK_NAME = "test-book-name";

    @Test
    public void testCreateBook() throws Exception {
        String endpoint = "/book";

        CreateBookDTO createBookDTO = new CreateBookDTO();
        createBookDTO.setName(TEST_BOOK_NAME);

        ApiHttpResponse<BookDTO> response = post(BookDTO.class, createBookDTO, endpoint);
        HttpResponse<String> httpResponse = response.getOriginalHttpResponse();
        BookDTO bookDTO = response.getResultBody();

        assertEquals(HttpStatus.CREATED_201.getStatusCode(), httpResponse.statusCode());

        assertTrue(bookDTO.getId() > 0);
        assertEquals(createBookDTO.getName(), bookDTO.getName());
    }
}
