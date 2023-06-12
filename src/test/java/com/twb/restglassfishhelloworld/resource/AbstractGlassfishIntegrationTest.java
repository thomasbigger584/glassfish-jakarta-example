package com.twb.restglassfishhelloworld.resource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.ContainerState;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

public class AbstractGlassfishIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger("IntegrationTest");

    private static final String DOCKER_COMPOSE_LOCATION = "src/test/resources/";
    private static final String TEST_DOCKER_COMPOSE_YML = "test-docker-compose.yml";
    private static final String GLASSFISH_SERVICE_NAME = "glassfish";
    private static final String MYSQL_DATABASE_SERVICE_NAME = "mysql_database";

    private static final String ARTIFACT_NAME = "RestGlassfishHelloWorld-1.0-SNAPSHOT";
    private static final int GLASSFISH_SERVICE_PORT = 8080;
    protected static final String API_BASE_URL = String.format("http://localhost:%d/%s/api", GLASSFISH_SERVICE_PORT, ARTIFACT_NAME);

    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
    private static final String DATABASE_SCHEMA_NAME = "helloworld";
    private static final String[] DATABASE_TABLE_NAMES = {"book"};
    private static final String EXECUTE_TRUNCATE_TABLES_SQL = "mysql -u %s -p%s -e 'use %s; truncate %s;'";

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

    @BeforeEach
    public void beforeEach() throws Exception {
        Optional<ContainerState> containerStateOpt =
                dockerComposeContainer.getContainerByServiceName(MYSQL_DATABASE_SERVICE_NAME);
        if (containerStateOpt.isPresent()) {
            ContainerState containerState = containerStateOpt.get();
            String tablesStr = Arrays.toString(DATABASE_TABLE_NAMES)
                    .replace("[", "")
                    .replace("]", "");
            containerState.execInContainer(String.format(EXECUTE_TRUNCATE_TABLES_SQL,
                    DATABASE_USERNAME, DATABASE_PASSWORD, DATABASE_SCHEMA_NAME, tablesStr));
        }
    }

    @AfterAll
    public static void afterAll() {
        dockerComposeContainer.stop();
    }
}
