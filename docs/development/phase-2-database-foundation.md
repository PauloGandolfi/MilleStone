# Phase 2 — Database Foundation

## Status

Completed on the `chore/phase-2-postgresql-flyway-foundation` branch.

## Objective

Establish the PostgreSQL persistence foundation for MilleStone without introducing financial-domain tables or business features prematurely.

## Implemented

- PostgreSQL 17.11 Alpine local service through Docker Compose.
- Persistent local PostgreSQL volume.
- Spring Data JPA with Hibernate schema validation (`ddl-auto: validate`).
- PostgreSQL JDBC driver.
- Flyway core and PostgreSQL database support.
- Initial Flyway migration creating the `milestone` application schema.
- Testcontainers PostgreSQL integration for backend tests.
- Spring Boot service connection support for Testcontainers.
- Integration assertions covering database connectivity, application schema creation, and Flyway migration execution.

## Local Configuration

Default local values are intentionally development-only and can be overridden with environment variables:

- `DB_HOST` (default `localhost`)
- `DB_PORT` (default `5432`)
- `DB_NAME` (default `milestone_dev`)
- `DB_USERNAME` (default `milestone_user`)
- `DB_PASSWORD` (default `milestone_pass`)

No production credentials or secrets are stored in the repository.

## Commands

Start PostgreSQL:

```bash
docker compose up -d postgres
```

Run backend tests:

```bash
cd backend
./mvnw clean test
```

Run the application:

```bash
cd backend
./mvnw spring-boot:run
```

Stop local infrastructure:

```bash
docker compose down
```

To also remove the local database volume:

```bash
docker compose down -v
```

## Scope Boundary

Phase 2 intentionally does not create `Category`, `Transaction`, `Budget`, `Goal`, or user tables. Domain modeling and persistence boundaries belong to the following phases.
