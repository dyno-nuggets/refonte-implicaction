package com.dynonuggets.refonteimplicaction.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.ContainerLaunchException;
import org.testcontainers.containers.MySQLContainer;

@DataJpaTest
@ActiveProfiles(profiles = "test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractContainerBaseTest {

    @SuppressWarnings("rawtypes")
    static final MySQLContainer container;

    static {
        container = new MySQLContainer<>("mysql:8")
                .withDatabaseName("implicaction")
                .withReuse(true);

        try {
            container.start();
        } catch (ContainerLaunchException ignored) {
        } finally {
            container.stop();
        }
    }
}
