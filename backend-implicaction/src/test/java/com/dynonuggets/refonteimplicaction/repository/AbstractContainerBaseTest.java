package com.dynonuggets.refonteimplicaction.repository;

import org.testcontainers.containers.MySQLContainer;

abstract class AbstractContainerBaseTest {

    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer<>("mysql:latest")
                .withDatabaseName("implicaction")
                .withReuse(true);
        MY_SQL_CONTAINER.start();
    }
}
