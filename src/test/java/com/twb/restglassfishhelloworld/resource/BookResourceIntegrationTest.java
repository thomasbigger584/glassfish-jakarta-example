package com.twb.restglassfishhelloworld.resource;

import com.twb.restglassfishhelloworld.dto.BookDTO;
import com.twb.restglassfishhelloworld.dto.CreateBookDTO;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.ContainerState;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookResourceIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(BookResourceIntegrationTest.class);

    private static final String DOCKER_COMPOSE_LOCATION = "src/test/resources/";
    private static final String TEST_DOCKER_COMPOSE_YML = "test-docker-compose.yml";
    private static final String GLASSFISH_SERVICE_NAME = "glassfish";
    private static final String ARTIFACT_NAME = "RestGlassfishHelloWorld-1.0-SNAPSHOT";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final int GLASSFISH_SERVICE_PORT = 8080;
    private static final String API_BASE_URL = String.format("http://localhost:%d/%s/api", GLASSFISH_SERVICE_PORT, ARTIFACT_NAME);
    private static final String TEST_BOOK_NAME = "test-book-name";

    public static DockerComposeContainer compose;

    @BeforeAll
    public static void beforeAll() {
        File file = new File(DOCKER_COMPOSE_LOCATION + TEST_DOCKER_COMPOSE_YML);
        compose = new DockerComposeContainer(file)
                .withExposedService(GLASSFISH_SERVICE_NAME, GLASSFISH_SERVICE_PORT)
                .withLogConsumer(GLASSFISH_SERVICE_NAME, new Slf4jLogConsumer(logger).withPrefix(GLASSFISH_SERVICE_NAME))
                .waitingFor(GLASSFISH_SERVICE_NAME, new LogMessageWaitStrategy()
                        .withRegEx(".*Successfully autodeployed : /opt/glassfish7/glassfish/domains/domain1/autodeploy/" + ARTIFACT_NAME + ".war.*"));
        compose.start();
    }

    @AfterAll
    public static void afterAll() {
        compose.stop();
    }

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

        int statusCode = response.statusCode();
        assertEquals(HttpStatus.CREATED_201.getStatusCode(), statusCode);

        assertEquals(HttpStatus.CREATED_201.getStatusCode(), response.statusCode());

        BookDTO bookDTO = objectMapper.readValue(response.body(), BookDTO.class);
        assertTrue(bookDTO.getId() > 0);
        assertEquals(createBookDTO.getName(), bookDTO.getName());
    }
}
