package io.github.paulogandolfi.milestone;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@Testcontainers
@SpringBootTest
class MilleStoneApplicationTests {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17.11-alpine")
            .withDatabaseName("milestone_test")
            .withUsername("milestone_test")
            .withPassword("milestone_test");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void connectsToPostgresAndRunsFlywayMigration() {
        Integer databaseConnection = jdbcTemplate.queryForObject("select 1", Integer.class);
        Long schemaCount = jdbcTemplate.queryForObject(
                "select count(*) from information_schema.schemata where schema_name = 'milestone'",
                Long.class);
        Long migrationCount = jdbcTemplate.queryForObject(
                "select count(*) from flyway_schema_history where version = '1' and success = true",
                Long.class);

        assertThat(databaseConnection).isEqualTo(1);
        assertThat(schemaCount).isEqualTo(1L);
        assertThat(migrationCount).isEqualTo(1L);
    }
}
