package com.dynonuggets.refonteimplicaction.repository;

import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractContainerBaseTest {

    @SuppressWarnings("rawtypes")
    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8")
                .withDatabaseName("implicaction")
                .withUsername("test")
                .withPassword("test")
                .withReuse(true);
        MY_SQL_CONTAINER.start();
    }
}
