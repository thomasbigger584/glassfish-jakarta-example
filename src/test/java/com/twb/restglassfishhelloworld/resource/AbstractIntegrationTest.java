package com.twb.restglassfishhelloworld.resource;

import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class AbstractIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger("IntegrationTest");
    private static final String DOCKER_COMPOSE_LOCATION = "src/test/resources/";
    private static final String TEST_DOCKER_COMPOSE_YML = "test-docker-compose.yml";
    private static final String GLASSFISH_SERVICE_NAME = "glassfish";
    private static final String ARTIFACT_NAME = "RestGlassfishHelloWorld-1.0-SNAPSHOT";
    private static final int GLASSFISH_SERVICE_PORT = 8080;
    protected static final String API_BASE_URL = String.format("http://localhost:%d/%s/api", GLASSFISH_SERVICE_PORT, ARTIFACT_NAME);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().build();
    public static DockerComposeContainer dockerComposeContainer;

    @BeforeAll
    public static void beforeAll() {
        File file = new File(DOCKER_COMPOSE_LOCATION + TEST_DOCKER_COMPOSE_YML);
        dockerComposeContainer = new DockerComposeContainer(file)
                .withExposedService(GLASSFISH_SERVICE_NAME, GLASSFISH_SERVICE_PORT)
                .withLogConsumer(GLASSFISH_SERVICE_NAME, new Slf4jLogConsumer(logger).withPrefix(GLASSFISH_SERVICE_NAME))
                .waitingFor(GLASSFISH_SERVICE_NAME, new LogMessageWaitStrategy()
                        .withRegEx(".*Successfully autodeployed : /opt/glassfish7/glassfish/domains/domain1/autodeploy/" + ARTIFACT_NAME + ".war.*"));
        dockerComposeContainer.start();
    }

    @AfterAll
    public static void afterAll() {
        dockerComposeContainer.stop();
    }

    protected static <ResultBody, RequestBody> ApiHttpResponse<ResultBody> post(Class<ResultBody> resultClass,
                                                                                RequestBody requestBody, String endpoint) throws Exception {
        String json = OBJECT_MAPPER.writeValueAsString(requestBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + endpoint))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return executeRequest(resultClass, request);
    }

    protected static <ResultBody> ApiHttpResponse<ResultBody> get(Class<ResultBody> resultClass, String endpoint) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + endpoint))
                .GET().build();
        return executeRequest(resultClass, request);
    }

    private static <ResultBody> ApiHttpResponse<ResultBody> executeRequest(Class<ResultBody> resultClass, HttpRequest request) throws Exception {
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        ResultBody result = OBJECT_MAPPER.readValue(response.body(), resultClass);
        return new ApiHttpResponse<>(response, result);
    }

    @Getter
    @RequiredArgsConstructor
    protected static class ApiHttpResponse<ResultBody> {
        private final HttpResponse<String> originalHttpResponse;
        private final ResultBody resultBody;
    }
}
