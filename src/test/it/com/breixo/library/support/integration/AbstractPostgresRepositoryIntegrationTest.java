package com.breixo.library.support.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/** The abstract Postgres repository integration test base; requires Docker for Testcontainers. */
@SpringBootTest
@ActiveProfiles("integration")
public abstract class AbstractPostgresRepositoryIntegrationTest {

    /** The Constant POSTGRESQL_DOCKER_IMAGE_NAME. */
    private static final String POSTGRESQL_DOCKER_IMAGE_NAME = "postgres:16-alpine";

    /** The shared PostgreSQL container for all repository integration tests. */
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(
            DockerImageName.parse(AbstractPostgresRepositoryIntegrationTest.POSTGRESQL_DOCKER_IMAGE_NAME));

    static {
        AbstractPostgresRepositoryIntegrationTest.POSTGRESQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void registerDatasource(final DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url",
                AbstractPostgresRepositoryIntegrationTest.POSTGRESQL_CONTAINER::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username",
                AbstractPostgresRepositoryIntegrationTest.POSTGRESQL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password",
                AbstractPostgresRepositoryIntegrationTest.POSTGRESQL_CONTAINER::getPassword);
    }
}
