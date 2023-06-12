package com.twb.restglassfishhelloworld.resource;

import com.twb.restglassfishhelloworld.dto.BookDTO;
import com.twb.restglassfishhelloworld.dto.CreateBookDTO;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookResourceIntegrationTest extends AbstractGlassfishIntegrationTest {
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String TEST_BOOK_NAME = "test-book-name";

    @Test
    public void testCreateBook() throws Exception {
        String endpoint = "/book";

        CreateBookDTO createBookDTO = new CreateBookDTO();
        createBookDTO.setName(TEST_BOOK_NAME);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(createBookDTO);

        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + endpoint))
                .header(CONTENT_TYPE_HEADER, MediaType.APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpStatus.CREATED_201.getStatusCode(), response.statusCode());

        BookDTO bookDTO = objectMapper.readValue(response.body(), BookDTO.class);
        assertTrue(bookDTO.getId() > 0);
        assertEquals(createBookDTO.getName(), bookDTO.getName());
    }
}
