# MilleStone Development Roadmap

> **Master Development Checklist & Implementation Plan**  
> Project: **MilleStone** — Personal Finance Web Application  
> Backend: Java / Spring Boot / PostgreSQL / Flyway  
> Frontend: React / TypeScript / Vite  
> Architecture: Modular Monolith (Clean / Hexagonal Ports & Adapters)  
> Base Package: `io.github.paulogandolfi.milestone`

---

## Roadmap Overview & Milestone Progression

This document defines the strict, top-to-bottom implementation sequence for MilleStone, taking the repository from initial setup to the first usable version, and through to a secure, tested, production-ready `v1.0` release. Follow each phase sequentially and mark checklist items as completed.

```text
Phase 0  — Repository & Workspace Preparation
    ↓
Phase 1  — Backend Foundation (Spring Boot & Tooling)
    ↓
Phase 2  — Database Foundation (PostgreSQL & Flyway)
    ↓
Phase 3  — Backend Architecture & Domain Foundation
    ↓
Phase 4  — Frontend Foundation (React, TypeScript & Vite)
    ↓
Phase 5  — Vertical Slice 0: Frontend ↔ Backend ↔ Database
    ↓
Phase 6  — Categories (Vertical Slice 1)
    ↓
Phase 7  — Transaction Management (Vertical Slice 2)
    ↓
🎯 FIRST USABLE MILESTONE
    ↓
Phase 8  — Financial Dashboard & Aggregations (Vertical Slice 3)
    ↓
Phase 9  — Recurring Transactions (Vertical Slice 4)
    ↓
Phase 10 — Monthly Budgets (Vertical Slice 5)
    ↓
Phase 11 — Financial Goals (Vertical Slice 6)
    ↓
Phase 12 — Authentication, Authorization & User Data Isolation
    ↓
Phase 13 — Financial Statement Imports (CSV / OFX)
    ↓
Phase 14 — API Scalability, Pagination & Advanced Filtering
    ↓
Phase 15 — Observability, Logging & Diagnostics
    ↓
Phase 16 — Security Hardening & Pre-Production Review
    ↓
Phase 17 — Containerization & CI/CD Pipeline
    ↓
Phase 18 — Production Deployment & Rollback Strategy
    ↓
🚀 MilleStone v1.0 Production Release
```

---

## Phase 0 — Repository & Workspace Preparation

### Objective
Establish the repository directory layout, Git hygiene, project documentation structure, and baseline developer tooling without committing premature application code.

### Architectural Decisions & References
* **ADR-001**: Web-First Architecture (React + Spring Boot)
* **ADR-002**: Modular Monolith Repository Structure

### Checklist

- [x] **Git Repository Verification**
  - [x] Initialize/verify Git repository root.
  - [x] Configure `.gitignore` covering Java/Maven (`target/`, `.mvn/wrapper/maven-wrapper.jar`), Node/Frontend (`node_modules/`, `dist/`), IDE files (`.idea/`, `.vscode/`, `*.iml`), OS artifacts (`.DS_Store`), and environment files (`.env`, `.env.local`).
  - [x] Establish standard Git branch strategy (`main` for production-ready code, feature branches `feat/*`, `fix/*`, `chore/*`).

- [x] **Directory Layout Setup**
  - [x] Create repository folder structure:
    ```text
    milestone/
    ├── backend/
    ├── frontend/
    ├── docs/
    │   ├── architecture/
    │   │   └── decisions/
    │   ├── development/
    │   └── product/
    ├── .gitignore
    ├── README.md
    └── docker-compose.yml (local development dependencies)
    ```

- [x] **Documentation Foundation**
  - [x] Maintain `README.md` aligned with the web-first scope and architecture.
  - [x] Create `docs/architecture/decisions/` for Architecture Decision Records (ADRs).
  - [x] Place this roadmap at `docs/development/development-roadmap.md`.

- [x] **Development Environment Prerequisites**
  - [x] Verify JDK 21+ installation (`java -version`).
  - [x] Verify Node.js (LTS version) and npm installation (`node -v`, `npm -v`).
  - [x] Verify Docker and Docker Compose installation for local services (`docker compose version`).

### Validation
- [x] Run `git status` — ensure working tree is clean and untracked files match `.gitignore` rules.
- [x] Directory layout matches the modular monolith specification.

### Done When
The repository contains a clean directory structure with `.gitignore`, initial documentation, and development environment prerequisites verified across team machines.

---

## Phase 1 — Backend Foundation (Spring Boot & Tooling)

> **Depends on:** Phase 0 completion.

### Objective
Initialize a minimal, runnable Spring Boot application in `backend/` using Maven with zero extraneous dependencies.

### Checklist

- [ ] **Project Bootstrapping**
  - [ ] Initialize Maven project in `backend/` with base package `io.github.paulogandolfi.milestone`.
  - [ ] Set up Maven Wrapper (`mvnw`, `mvnw.cmd`, `.mvn/wrapper/`).
  - [ ] Configure `pom.xml` with Spring Boot parent and minimum initial dependencies:
    - `spring-boot-starter-web`
    - `spring-boot-starter-validation`
    - `spring-boot-starter-test` (JUnit 5, Mockito, AssertJ)

- [ ] **Application Entrypoint & Base Configuration**
  - [ ] Create main class `io.github.paulogandolfi.milestone.MilestoneApplication`.
  - [ ] Create `src/main/resources/application.yml` with default server port (`server.port=8080`) and application name (`spring.application.name=milestone-api`).
  - [ ] Create profile-specific configuration file `application-local.yml` for local development.

- [ ] **Initial Health/Verification Endpoint**
  - [ ] Create basic system info/health check REST controller (`/api/v1/system/ping`) returning application timestamp and status.

- [ ] **Baseline Test Verification**
  - [ ] Create `MilestoneApplicationTests` verifying Spring application context loads without failure.
  - [ ] Verify unit test execution via Maven.

### Example Commands
```bash
cd backend
./mvnw clean test
./mvnw spring-boot:run
curl http://localhost:8080/api/v1/system/ping
```

### Validation
- [ ] `./mvnw clean test` passes with 0 failures.
- [ ] Application starts locally and responds `200 OK` on `/api/v1/system/ping`.

### Done When
The Spring Boot backend starts cleanly, executes its initial context load test, and responds to a simple HTTP health ping.

---

## Phase 2 — Database Foundation (PostgreSQL & Flyway)

> **Depends on:** Phase 1 completion.

### Objective
Set up PostgreSQL for local development using Docker Compose, integrate Flyway for reproducible schema migrations, and configure Spring Data JPA with schema validation.

### Architectural Rules
* Never use `hibernate.hbm2ddl.auto=create` or `update` for schema management. Always use `validate`.
* All database changes must be versioned via Flyway migration scripts (`V1__...sql`).
* Docker Compose here is strictly for local developer productivity (database container); production containerization belongs to later operational phases.

### Checklist

- [ ] **Local PostgreSQL Environment**
  - [ ] Define PostgreSQL 16+ service in root `docker-compose.yml`:
    - Database name: `milestone_dev`
    - Port mapping: `5432:5432`
    - Persistent volume for data.
    - Environment variables for user/password with sensible local defaults.
  - [ ] Configure Testcontainers for automated database integration tests.

- [ ] **Backend Database Dependencies**
  - [ ] Add dependencies to `backend/pom.xml`:
    - `org.springframework.boot:spring-boot-starter-data-jpa`
    - `org.postgresql:postgresql` (runtime)
    - `org.flywaydb:flyway-core`
    - `org.flywaydb:flyway-database-postgresql`
    - `org.testcontainers:junit-jupiter` & `org.testcontainers:postgresql` (test scope)

- [ ] **Spring Data JPA & Flyway Configuration**
  - [ ] Configure `application.yml` and `application-local.yml`:
    ```yaml
    spring:
      datasource:
        url: ${DB_URL:jdbc:postgresql://localhost:5432/milestone_dev}
        username: ${DB_USERNAME:milestone_user}
        password: ${DB_PASSWORD:milestone_pass}
      jpa:
        hibernate:
          ddl-auto: validate
        open-in-view: false
        properties:
          hibernate.dialect: org.hibernate.dialect.PostgreSQLDialect
      flyway:
        enabled: true
        locations: classpath:db/migration
    ```

- [ ] **First Migration Script**
  - [ ] Create `backend/src/main/resources/db/migration/V1__init_schema.sql` (baseline schema metadata).

- [ ] **Database Integration Test**
  - [ ] Create a database integration test with Testcontainers verifying PostgreSQL connection and Flyway migration execution during test runs.

### Example Commands
```bash
# Start local database
docker compose up -d postgres

# Run backend with migrations
cd backend
./mvnw clean test
```

### Validation
- [ ] Flyway runs on application startup and logs migration execution.
- [ ] Testcontainers spins up an isolated PostgreSQL instance and runs migrations during `mvn test`.
- [ ] Hibernate validation succeeds (`ddl-auto: validate`).

### Done When
PostgreSQL runs locally in Docker, Flyway controls the database schema versioning, and backend tests run against real PostgreSQL via Testcontainers.

---

## Phase 3 — Backend Architecture & Domain Foundation

> **Depends on:** Phase 2 completion.

### Objective
Establish the minimal Ports & Adapters (Hexagonal / Clean) package structure, cross-cutting components (global exception handling, standard API error responses), and basic monetary representation without creating empty or over-engineered abstractions.

### Architecture Guidelines
* **Avoid Architecture for Architecture's Sake**: Prefer clear code and meaningful boundaries over architectural ceremony. Do not introduce ports, interfaces, factories, or strategies unless they serve a concrete decoupling requirement (e.g. separating domain code from JPA).
* **Domain Layer**: Pure Java, zero dependencies on Spring, JPA, HTTP, or Jackson. Contains entities, value objects, domain exceptions, and repository interfaces (Outbound Ports).
* **Application Layer**: Use cases / input ports coordinating business logic and driving domain operations.
* **Adapters (Inbound/Web)**: REST Controllers, Request/Response DTOs, Bean Validation, Mappers.
* **Adapters (Outbound/Persistence)**: JPA Entities, Spring Data Repositories, Persistence Adapters, Schema Mappers.
* **Future Multi-User Preparation**: MilleStone begins in a simple single-user development mode so business logic can be validated rapidly. However, domain and persistence models must avoid assumptions (e.g. global singletons or rigid constraints) that would make associating entities with an owner (`User`) difficult later.

### Monetary Representation Principles
* Financial values must use exact decimal representation (`BigDecimal`). Never use `float` or `double`.
* The `Money` value object must remain intentionally small. Its initial responsibilities are limited to what MilleStone genuinely needs: `amount` (scaled `BigDecimal`), currency validation (default BRL), non-negative checks, equality, comparison, and basic addition/subtraction.
* Do NOT design a complete financial mathematics framework or abstract currency conversion engine.

> 💡 **Core Principle:** *Money exists to guarantee financial correctness, not to become an internal monetary framework.*

### Checklist

- [ ] **Package Structure Definition**
  - [ ] Setup base package structure under `io.github.paulogandolfi.milestone`:
    ```text
    io.github.paulogandolfi.milestone/
    ├── common/
    │   ├── domain/               # Base domain types (Money, Value Objects)
    │   ├── error/                # Domain exceptions & error codes
    │   └── rest/                 # Global error response DTOs & handlers
    └── modules/                  # (Feature modules created as slices arrive)
    ```

- [ ] **Domain Monetary Value Object**
  - [ ] Create `Money` value object encapsulating `BigDecimal` with scale of 2, standard rounding (`RoundingMode.HALF_EVEN`), and currency representation (BRL).
  - [ ] Implement core operations: `plus()`, `minus()`, `isPositive()`, `isZero()`, `compareTo()`.
  - [ ] Enforce invariants: amount cannot be null; reject NaN / infinite values; prevent invalid scale.

- [ ] **Global Error Handling & Standard API Error Response**
  - [ ] Create standard API error payload DTO:
    ```json
    {
      "timestamp": "2026-08-31T12:00:00Z",
      "status": 400,
      "error": "Bad Request",
      "code": "VALIDATION_FAILED",
      "message": "Invalid transaction amount",
      "fieldErrors": [
        { "field": "amount", "message": "Amount must be greater than zero" }
      ]
    }
    ```
  - [ ] Implement `@RestControllerAdvice` (`GlobalExceptionHandler`) to handle:
    - `MethodArgumentNotValidException` (Bean validation errors, HTTP 400)
    - `ResourceNotFoundException` / `EntityNotFoundException` (HTTP 404)
    - `DomainException` / Business rule violations (HTTP 400 or HTTP 422)
    - `ConflictException` (e.g. duplicate resource names, HTTP 409)
    - Unexpected runtime errors (HTTP 500 with sanitized message)

- [ ] **Unit Tests for Core Architecture**
  - [ ] Unit tests for `Money` value object invariants, scale enforcement, and arithmetic operations.
  - [ ] Unit tests for `GlobalExceptionHandler` verifying translation of validation, domain, and missing-resource exceptions to standard HTTP error DTOs.

### Validation
- [ ] Domain code has zero imports from `org.springframework.*` or `jakarta.persistence.*`.
- [ ] Global exception handler transforms validation and domain errors into structured JSON responses.

### Done When
Core domain primitives (`Money`), base exceptions, and global REST error handling are tested and ready for business feature implementation.

---

## Phase 4 — Frontend Foundation (React, TypeScript & Vite)

> **Depends on:** Phase 0 completion.

### Objective
Initialize a modern, type-safe React application in `frontend/` using Vite, TypeScript, React Router, and a feature-based architecture.

### Checklist

- [ ] **Project Bootstrapping**
  - [ ] Scaffold React + TypeScript application with Vite inside `frontend/`.
  - [ ] Configure `tsconfig.json` with strict type checking (`strict: true`).
  - [ ] Configure path aliases in `vite.config.ts` and `tsconfig.json` (e.g. `@/*` -> `src/*`).

- [ ] **Core Dependencies Installation**
  - [ ] Install `react-router-dom` for client-side routing.
  - [ ] Install HTTP client solution (standard `fetch` wrapper or `axios`).
  - [ ] Set up styling foundation (CSS modules, Tailwind CSS, or scoped CSS as preferred).

- [ ] **Frontend Directory Layout**
  - [ ] Organize `frontend/src/` into a feature-oriented structure:
    ```text
    frontend/src/
    ├── app/                     # Router, Application Providers, Layouts
    │   ├── App.tsx
    │   ├── router.tsx
    │   └── Layout.tsx
    ├── assets/                  # Static assets, icons
    ├── components/              # Shared, generic UI components (Button, Modal, Input)
    │   ├── ui/
    │   └── feedback/            # LoadingSpinner, ErrorAlert, EmptyState
    ├── features/                # Domain-specific feature modules
    │   ├── categories/
    │   ├── transactions/
    │   ├── dashboard/
    │   ├── budgets/
    │   └── goals/
    ├── hooks/                   # Shared custom hooks
    ├── services/                # Shared API clients & HTTP wrappers
    ├── types/                   # Shared global TypeScript types (ApiError, etc.)
    └── main.tsx
    ```

- [ ] **Environment Configuration & API Base Client**
  - [ ] Create `.env.example` and `.env.development` with `VITE_API_BASE_URL=http://localhost:8080/api/v1`.
  - [ ] Create centralized HTTP API client (`src/services/apiClient.ts`) handling base URLs, JSON content-type, and standard error response parsing.

- [ ] **Base Layout & Shell**
  - [ ] Create responsive application layout (`Layout.tsx`) with:
    - Top header / branding (MilleStone).
    - Navigation bar (Dashboard, Transactions, Categories, Budgets, Goals).
    - Main content area with `Outlet`.
    - Notification / toast or global error boundary placeholder.

- [ ] **Shared UI State Components**
  - [ ] Build reusable `LoadingSpinner`, `ErrorAlert`, and `EmptyState` components.

### Example Commands
```bash
cd frontend
npm install
npm run dev
npm run build
```

### Validation
- [ ] `npm run dev` starts the Vite development server on `http://localhost:5173`.
- [ ] `npm run build` compiles TypeScript and bundles the app without errors.
- [ ] Navigation between placeholder routes (Dashboard, Transactions) works via React Router.

### Done When
The React + TypeScript application runs with a responsive layout, router, configured API client, and clean feature-based folder structure.

---

## Phase 5 — Vertical Slice 0: Frontend ↔ Backend ↔ Database

> **Depends on:** Phase 1, Phase 2, Phase 3, and Phase 4 completion.

### Objective
Prove end-to-end communication from the browser through React, HTTP, Spring Boot, and PostgreSQL back to the UI before implementing business logic.

```text
Browser (React) ──HTTP GET /api/v1/system/status──> Spring Boot ──SELECT 1──> PostgreSQL
        UI Component <──JSON { status: "UP", db: "CONNECTED" } <───────────────┘
```

### Checklist

- [ ] **Backend Health/Status API**
  - [ ] Create `SystemStatusController` at `GET /api/v1/system/status`.
  - [ ] Query database connectivity in the status endpoint (e.g. check datasource status).
  - [ ] Return JSON payload with application version, environment, and database status.
  - [ ] Configure Spring CORS configuration in `application-local.yml` allowing `http://localhost:5173`.

- [ ] **Frontend System Status Check**
  - [ ] Create `systemService.ts` calling `/api/v1/system/status`.
  - [ ] Add a small status indicator in the UI layout showing backend and database connectivity.
  - [ ] Handle loading state and network failure gracefully (e.g., "Connecting to MilleStone API...").

- [ ] **Integration Verification Test**
  - [ ] Backend test for `/api/v1/system/status` returning HTTP 200.
  - [ ] Verify CORS headers allow requests from Vite dev server.

### Validation
- [ ] Open `http://localhost:5173` in browser; the UI indicates active connection to the backend and database.
- [ ] Shutting down Spring Boot causes the UI to show an appropriate "Backend unreachable" state without crashing.

> 🎯 **Milestone:** First end-to-end technical communication completed across the full stack.

### Done When
The complete technical pipeline (React ↔ HTTP ↔ Spring Boot ↔ PostgreSQL) is proven to work seamlessly end-to-end.

---

## Phase 6 — Categories (Vertical Slice 1)

> **Depends on:** Phase 5 completion.  
> **Rationale:** Transactions require classification. Building a minimal, robust Category management vertical slice first enables Transaction creation and classification without unnecessary blockers.

### Objective
Implement a minimal Category management slice allowing users to view default categories and create custom categories for classifying income and expenses.

### Domain Model & Rules
* `Category`:
  * `id`: UUID (or Long)
  * `name`: String (non-blank, max 50 chars, unique)
  * `type`: Enum (`INCOME`, `EXPENSE`, `BOTH`)
  * `color`: Hex code or simple color identifier (e.g., `#4F46E5`)
  * `isDefault`: Boolean (system-seeded vs custom)
* **Scope Guardrail**: Keep Categories deliberately minimal. Do NOT prematurely implement nested hierarchies, subcategories, complex icon engines, custom color pickers, or category analytics unless explicitly required by a core feature.
* **Multi-User Readiness Note**: The initial schema/domain runs in single-user mode, but avoids assumptions that would prevent associating categories with an owner (`user_id`) in Phase 12.

### Checklist

- [ ] **Database Migration**
  - [ ] Create `V2__create_categories_table.sql`:
    - `categories` table with constraints and indexes.
    - Seed standard default categories (Housing, Food, Transportation, Salary, Investments, Leisure).

- [ ] **Backend Domain & Ports**
  - [ ] Create `Category` domain entity and `CategoryType` enum in `category/domain/`.
  - [ ] Define `CategoryRepositoryPort` outbound interface.

- [ ] **Backend Persistence Adapter**
  - [ ] Create JPA entity `CategoryJpaEntity` and `SpringDataCategoryRepository`.
  - [ ] Implement `CategoryPersistenceAdapter` implementing `CategoryRepositoryPort`.
  - [ ] Map between JPA entities and Domain entities.

- [ ] **Backend Application Use Cases**
  - [ ] Create `ListCategoriesUseCase` (retrieves all categories, optionally filtered by `CategoryType`).
  - [ ] Create `CreateCategoryUseCase` (validates unique name, builds domain entity, persists).

- [ ] **Backend REST Inbound Adapter**
  - [ ] Create `CategoryController` (`/api/v1/categories`):
    - `GET /api/v1/categories` (supports optional `?type=EXPENSE|INCOME`)
    - `POST /api/v1/categories` (201 Created)
  - [ ] Create `CreateCategoryRequest` DTO with Bean Validation (`@NotBlank`, `@NotNull`, `@Size(max=50)`).
  - [ ] Create `CategoryResponse` DTO.

- [ ] **Backend Tests**
  - [ ] Unit tests for `Category` domain rules and use cases.
  - [ ] Integration test for `CategoryPersistenceAdapter` with Testcontainers.
  - [ ] WebMvc controller test for `/api/v1/categories` verifying validation and status codes.

- [ ] **Frontend Category Feature**
  - [ ] Create `src/features/categories/` types, API service, and hooks.
  - [ ] Create `CategorySelect` dropdown component for use across forms.
  - [ ] Create `CategoryList` and `CreateCategoryModal/Form` in Categories page.
  - [ ] Handle loading, error, and empty states.

### Validation
- [ ] Seeded categories load on application start.
- [ ] User can view categories and create a new custom category from the web interface.
- [ ] Invalid category submissions return validation error messages on form fields.

### Done When
Categories can be listed and created via the web frontend, persisted in PostgreSQL, and ready to be associated with Transactions.

---

## Phase 7 — Transaction Management (Vertical Slice 2)

> **Depends on:** Phase 6 (Categories) completion.  
> **Do not start before:** Categories can be retrieved from backend.

### Objective
Implement the foundational business capability of MilleStone: complete CRUD management of financial transactions (Income & Expense) with strict monetary integrity, consistent REST API conventions, and full frontend-to-backend persistence.

```text
User fills Form (Amount, Date, Type, Category, Description)
       ↓
React Validation & API Call (POST /api/v1/transactions)
       ↓
Spring Boot TransactionController (DTO Validation)
       ↓
CreateTransactionUseCase (Domain Rule Verification)
       ↓
Transaction Domain Entity (Money Invariant Protection)
       ↓
TransactionPersistenceAdapter ──> PostgreSQL (transactions table)
       ↓
Response 201 Created ──> React updates Transaction List
```

### Domain Model & Rules
* `Transaction`:
  * `id`: UUID (or Long)
  * `description`: String (required, 1-100 characters)
  * `amount`: `Money` (strictly positive `BigDecimal`, scaled to 2 decimals)
  * `type`: Enum (`INCOME`, `EXPENSE`)
  * `date`: `LocalDate` (transaction date)
  * `categoryId`: ID reference to `Category` (must exist)
  * `source`: Enum (`MANUAL` initially; future: `IMPORT`, `AUTOMATION`)
  * `notes`: Optional String (max 500 chars)
  * `createdAt`, `updatedAt`: Timestamps
* **Multi-User Readiness Note**: The schema/domain initially operates in single-user mode; avoid constraints that prevent adding a `user_id` owner column in Phase 12.

### API Standards & Conventions Established Here
All business APIs from this phase onward must consistently adhere to:
* **REST Resource Naming**: Clear pluralized resource nouns (`/api/v1/transactions`, `/api/v1/transactions/{id}`).
* **DTO Separation**: Dedicated request and response DTOs (never expose domain or JPA entities directly).
* **HTTP Status Codes**: `200 OK` for reads/updates, `201 Created` with location/body for creations, `204 No Content` for deletions, `400 Bad Request` for validation failures, `404 Not Found` for missing resources.
* **Bean Validation**: Strict input validation (`@NotBlank`, `@NotNull`, `@Positive`, `@PastOrPresent`).
* **Consistent Error Contract**: Standard error JSON payload handled uniformly by `GlobalExceptionHandler`.

### Checklist

- [ ] **Database Migration**
  - [ ] Create `V3__create_transactions_table.sql`:
    - Columns: `id`, `description`, `amount` (`NUMERIC(19,2)`), `type`, `date`, `category_id` (FK to `categories`), `source`, `notes`, `created_at`, `updated_at`.
    - Indexes on `date`, `category_id`, and `type`.

- [ ] **Backend Domain & Outbound Ports**
  - [ ] Create `Transaction` domain entity with domain validation invariants.
  - [ ] Create `TransactionType` (`INCOME`, `EXPENSE`) and `TransactionSource` (`MANUAL`).
  - [ ] Define `TransactionRepositoryPort`:
    - `save(Transaction transaction): Transaction`
    - `findById(TransactionId id): Optional<Transaction>`
    - `findAll(TransactionFilter filter): List<Transaction>`
    - `deleteById(TransactionId id): void`

- [ ] **Backend Persistence Adapter**
  - [ ] Create `TransactionJpaEntity` and `SpringDataTransactionRepository`.
  - [ ] Implement `TransactionPersistenceAdapter` with query methods (filtering by date range, category, type).
  - [ ] Create mapper between domain and JPA entity.

- [ ] **Backend Application Use Cases**
  - [ ] `CreateTransactionUseCase`: Validates category existence, builds transaction, persists.
  - [ ] `GetTransactionUseCase`: Fetches transaction by ID or throws `EntityNotFoundException`.
  - [ ] `ListTransactionsUseCase`: Retrieves transactions with optional date range/type filters.
  - [ ] `UpdateTransactionUseCase`: Modifies transaction details with domain validation.
  - [ ] `DeleteTransactionUseCase`: Deletes transaction by ID.

- [ ] **Backend REST Inbound Adapter**
  - [ ] Create `TransactionController` (`/api/v1/transactions`):
    - `POST /api/v1/transactions` (201 Created)
    - `GET /api/v1/transactions` (200 OK, supports query params `startDate`, `endDate`, `type`, `categoryId`)
    - `GET /api/v1/transactions/{id}` (200 OK / 404 Not Found)
    - `PUT /api/v1/transactions/{id}` (200 OK / 404 Not Found)
    - `DELETE /api/v1/transactions/{id}` (204 No Content / 404 Not Found)
  - [ ] Create `CreateTransactionRequest`, `UpdateTransactionRequest`, and `TransactionResponse` DTOs.
  - [ ] Apply Bean Validation constraints (`@NotBlank`, `@Positive`, `@NotNull`, `@PastOrPresent`/valid date).

- [ ] **Backend Automated Tests**
  - [ ] Unit tests for `Transaction` entity validation (rejection of zero/negative amounts, blank descriptions).
  - [ ] Unit tests for all Use Cases with Mockito.
  - [ ] Integration tests for `TransactionPersistenceAdapter` and repository queries using Testcontainers.
  - [ ] WebMvc tests for `TransactionController` verifying validation, error responses, and HTTP status codes.

- [ ] **Frontend Transaction Feature**
  - [ ] Define TypeScript types in `src/features/transactions/types.ts`.
  - [ ] Create `transactionService.ts` for all API operations.
  - [ ] Create `TransactionForm` component:
    - Inputs for Type (toggle Income/Expense), Description, Amount (formatted currency input), Date, Category (dropdown from Phase 6), Notes.
    - Client-side validation.
  - [ ] Create `TransactionList` and `TransactionItem` components:
    - Display date, description, category badge, and formatted amount (+ green for income, - red for expense).
    - Action buttons: Edit, Delete (with confirmation dialog).
  - [ ] Create `TransactionFilterBar` (filter by month/year, type, category).
  - [ ] Create Edit Modal / Page updating existing transactions.
  - [ ] Ensure proper loading, empty, and error feedback states.

- [ ] **Frontend Automated Tests**
  - [ ] Test `TransactionForm` validation rules and submission handling.
  - [ ] Test `TransactionList` rendering and empty state.

- [ ] **End-to-End User Flow Validation**
  - [ ] Open MilleStone in a browser.
  - [ ] Create or select a category.
  - [ ] Register an income transaction.
  - [ ] Register an expense transaction.
  - [ ] View transactions in the list.
  - [ ] Edit a transaction and verify updated fields.
  - [ ] Delete a transaction.
  - [ ] Refresh the browser and verify data remains persisted in PostgreSQL.

### Validation
- [ ] Complete end-to-end user transaction lifecycle executes without error.
- [ ] Amounts are formatted correctly and stored with exact decimal precision in PostgreSQL (`numeric(19,2)`).

> 🎯 **Milestone: FIRST USABLE MILESTONE (First Usable MilleStone Version)**  
> *MilleStone can now be used for basic personal financial tracking: categories and transactions can be created, viewed, edited, deleted, and permanently persisted in PostgreSQL.*

### Done When
A user can perform full CRUD operations on transactions through the React web interface, verified by automated tests and persisted accurately in PostgreSQL.

---

## Phase 8 — Financial Dashboard & Aggregations (Vertical Slice 3)

> **Depends on:** Phase 7 (Transaction Management) completion.  
> **Do not start before:** Transactions can be created and queried by date range.

### Objective
Provide users with an authoritative, real-time financial overview for a selected month, calculated strictly on the backend.

### Architectural Rules
* **Backend as Single Source of Truth**: React is **NOT** the authoritative calculator for financial totals. All totals, balances, and category aggregations must be computed authoritatively by the backend domain/application layer. The frontend only formats and visualizes backend-provided figures.

### Core Metrics (Monthly)
1. **Total Income**: Sum of all `INCOME` transactions in period.
2. **Total Expenses**: Sum of all `EXPENSE` transactions in period.
3. **Net Balance**: `Total Income - Total Expenses`.
4. **Expense Breakdown by Category**: Category name, color, total spent, percentage of total expenses.
5. **Recent Transactions**: Last 5-10 transactions.

### Checklist

- [ ] **Backend Aggregation Queries & Use Case**
  - [ ] Add aggregation query methods in `TransactionRepositoryPort` / JPA repository (e.g. `SUM(amount) GROUP BY category`).
  - [ ] Create `GetMonthlyDashboardUseCase`:
    - Inputs: `year`, `month` (defaults to current calendar month).
    - Computes `totalIncome`, `totalExpense`, `netBalance`.
    - Computes category expense distribution (amount and percentage).
    - Fetches recent transactions.
  - [ ] Create `DashboardSummaryResponse` and `CategoryExpenseSummaryDto`.

- [ ] **Backend REST Endpoint**
  - [ ] Create `DashboardController` at `GET /api/v1/dashboard/monthly?year=YYYY&month=MM`.
  - [ ] Validate year/month query parameters.

- [ ] **Backend Tests**
  - [ ] Unit tests for dashboard calculation logic (verifying balance with mixed income/expenses, zero-transaction edge cases).
  - [ ] Integration tests verifying SQL aggregation accuracy against PostgreSQL.

- [ ] **Frontend Dashboard Feature**
  - [ ] Create `src/features/dashboard/` types and `dashboardService.ts`.
  - [ ] Create `MonthSelector` component to switch between months.
  - [ ] Create summary metric cards: `IncomeCard`, `ExpenseCard`, `NetBalanceCard` (color-coded).
  - [ ] Create `CategoryExpenseChart` / `CategoryExpenseList` showing breakdown with progress/percentage bars.
  - [ ] Create `RecentTransactionsWidget` linking directly to full transaction view.
  - [ ] Handle loading skeleton states, error banners, and empty month states ("No transactions recorded for this month").

- [ ] **Frontend Tests**
  - [ ] Test metric card rendering with positive, negative, and zero values.
  - [ ] Test month switcher triggering data refresh.

### Validation
- [ ] Navigating to the Dashboard displays current month calculated totals matching transactions in the database.
- [ ] Adding a new transaction immediately reflects in the dashboard upon return or refresh.
- [ ] Switching months updates all cards and category breakdowns correctly.

### Done When
The user has a responsive, accurate dashboard displaying financial metrics and category breakdowns calculated authoritatively by the backend.

---

## Phase 9 — Recurring Transactions (Vertical Slice 4)

> **Depends on:** Phase 7 (Transactions) and Phase 6 (Categories) completion.

### Objective
Allow users to register recurring income and expenses (e.g., monthly rent, salary, subscriptions) and preview or generate confirmed transactions with strict idempotency protection and without premature background scheduling complexity.

### Domain Model & Rules
* `RecurringRule`:
  * `id`: UUID
  * `description`: String
  * `amount`: `Money`
  * `type`: Enum (`INCOME`, `EXPENSE`)
  * `categoryId`: ID
  * `frequency`: Enum (`MONTHLY`, `WEEKLY`, `YEARLY`)
  * `startDate`: `LocalDate`
  * `endDate`: Optional `LocalDate`
  * `dayOfPeriod`: Integer (e.g., day 10 of the month)
  * `isActive`: Boolean
* **Projected vs. Confirmed Transactions**:
  * Clearly model the distinction between **Projected/Upcoming** occurrences (computed on demand for balance forecasting) and **Confirmed** transactions (materialized in `transactions`).
  * Supports calculations such as *Current Balance* vs. *Projected Balance*.
* **Idempotency as a Core Business Requirement**:
  * Re-running the generation process for a period must **never** create duplicate transactions. E.g., Rent of R$ 1,500 on Day 10 processed three times must result in exactly one transaction for that month.
* **No Premature Background Schedulers**:
  * Generation is triggered on-demand via use cases or application flows. Do not introduce complex cron daemons or queue workers prematurely.

### Checklist

- [ ] **Database Migration**
  - [ ] Create `V4__create_recurring_rules_table.sql` with foreign keys to `categories`.

- [ ] **Backend Domain & Repository**
  - [ ] Create `RecurringRule` entity, `Frequency` enum, and `RecurringRuleRepositoryPort`.
  - [ ] Create JPA entity, repository, and persistence adapter.

- [ ] **Backend Use Cases**
  - [ ] `CreateRecurringRuleUseCase`, `UpdateRecurringRuleUseCase`, `ToggleRuleStatusUseCase`.
  - [ ] `ListRecurringRulesUseCase`.
  - [ ] `GetUpcomingRecurringTransactionsUseCase`: Projects pending occurrences for a target date range that have not yet been materialized into `transactions`.
  - [ ] `ConfirmRecurringTransactionUseCase`: Generates a concrete `Transaction` record from a rule occurrence with strict idempotency verification (preventing double-creation for the same period).

- [ ] **Backend REST Endpoints**
  - [ ] Create `RecurringRuleController` (`/api/v1/recurring-rules`):
    - `GET /api/v1/recurring-rules`
    - `POST /api/v1/recurring-rules`
    - `PUT /api/v1/recurring-rules/{id}`
    - `PATCH /api/v1/recurring-rules/{id}/status`
    - `GET /api/v1/recurring-rules/upcoming?month=MM&year=YYYY`
    - `POST /api/v1/recurring-rules/{id}/generate`
  - [ ] Request/Response DTOs with validation.

- [ ] **Backend Tests**
  - [ ] Unit tests for recurrence date calculation (handling month ends like Feb 28/29, day 31).
  - [ ] **Idempotency Tests**: Verify that invoking transaction generation multiple times for the same rule and period produces exactly one transaction and rejects duplicates.
  - [ ] Projection tests verifying upcoming occurrences without modifying the database.
  - [ ] Integration tests for persistence and REST endpoints.

- [ ] **Frontend Recurring Feature**
  - [ ] Create `src/features/recurring/` with types, service, and components.
  - [ ] Create recurring rules list view with status toggle (Active / Paused).
  - [ ] Create Recurring Rule Form (frequency, recurrence day, start/end date).
  - [ ] Add "Upcoming Recurring Bills" widget to the Dashboard with one-click "Mark as Paid / Confirm" action.

### Validation
- [ ] User can define a monthly subscription or salary.
- [ ] Upcoming recurring items appear in the dashboard for the current month.
- [ ] Clicking "Confirm" creates a real transaction in the transactions list and prevents duplicate generation on subsequent clicks.

### Done When
Recurring income and expenses can be created, managed, projected, and converted into confirmed transactions with verified idempotency.

---

## Phase 10 — Monthly Budgets (Vertical Slice 5)

> **Depends on:** Phase 7 (Transactions) and Phase 6 (Categories) completion.

### Objective
Enable users to set monthly spending limits per category, track consumption percentage in real time, and receive visual indicators when approaching or exceeding limits.

### Domain Model & Rules
* `MonthlyBudget`:
  * `id`: UUID
  * `categoryId`: Category ID (unique per month/year)
  * `year`: Integer
  * `month`: Integer (1-12)
  * `plannedAmount`: `Money` (maximum spending limit)
* **Calculated Metrics (Backend-Authoritative)**:
  * `spentAmount`: Sum of actual expenses in that category for the specified month.
  * `remainingAmount`: `plannedAmount - spentAmount`.
  * `usagePercentage`: `(spentAmount / plannedAmount) * 100`.
  * `status`: `NORMAL` (<80%), `WARNING` (80-100%), `EXCEEDED` (>100%).

### Checklist

- [ ] **Database Migration**
  - [ ] Create `V5__create_monthly_budgets_table.sql`:
    - Unique constraint on `(category_id, year, month)`.

- [ ] **Backend Domain & Repository**
  - [ ] Create `MonthlyBudget` domain entity and `BudgetRepositoryPort`.
  - [ ] Implement JPA entity, repository, and persistence adapter.

- [ ] **Backend Use Cases**
  - [ ] `SetMonthlyBudgetUseCase`: Creates or updates budget for category and month.
  - [ ] `GetMonthlyBudgetOverviewUseCase`: Aggregates planned budgets with real-time actual transaction spending for the month.
  - [ ] `DeleteMonthlyBudgetUseCase`.

- [ ] **Backend REST Endpoints**
  - [ ] Create `BudgetController` (`/api/v1/budgets`):
    - `GET /api/v1/budgets?year=YYYY&month=MM`
    - `POST /api/v1/budgets` (Set/Update budget)
    - `DELETE /api/v1/budgets/{id}`

- [ ] **Backend Tests**
  - [ ] Unit tests for budget progress, remaining calculations, and status thresholds.
  - [ ] Integration tests verifying spent amount calculation joins transactions correctly.

- [ ] **Frontend Budget Feature**
  - [ ] Create `src/features/budgets/` types, services, and hooks.
  - [ ] Create Budget Management page with month selector.
  - [ ] Create `BudgetProgressBar` component with color states (green -> yellow -> red).
  - [ ] Create `SetBudgetModal` for creating/updating category limits.
  - [ ] Add compact Budget summary widget to the main Dashboard.

### Validation
- [ ] User can set a R$ 500 monthly budget for "Food".
- [ ] Spending R$ 250 shows 50% usage with R$ 250 remaining.
- [ ] Spending R$ 550 shows 110% usage and an `EXCEEDED` warning.
- [ ] Budget overview automatically reflects new or updated transactions.

### Done When
Users can configure monthly category budgets and track spending progress with real-time visual status calculated authoritatively by the backend.

---

## Phase 11 — Financial Goals (Vertical Slice 6)

> **Depends on:** Phase 5 (Core Integration) completion.

### Objective
Allow users to create and track specific savings goals (e.g., Emergency Fund, Vacation, New Car) with target amounts, target dates, and manual or linked progress deposits.

### Domain Model & Rules
* `FinancialGoal`:
  * `id`: UUID
  * `title`: String (e.g. "Emergency Reserve")
  * `targetAmount`: `Money` (strictly positive)
  * `currentAmount`: `Money` (>= 0)
  * `targetDate`: Optional `LocalDate`
  * `status`: Enum (`IN_PROGRESS`, `COMPLETED`, `CANCELLED`)
  * `createdAt`, `updatedAt`: Timestamps

### Checklist

- [ ] **Database Migration**
  - [ ] Create `V6__create_financial_goals_table.sql`.

- [ ] **Backend Domain & Repository**
  - [ ] Create `FinancialGoal` domain entity with deposit/withdraw domain methods and completion logic.
  - [ ] Implement `FinancialGoalRepositoryPort`, JPA entity, and persistence adapter.

- [ ] **Backend Use Cases**
  - [ ] `CreateGoalUseCase`, `UpdateGoalUseCase`, `DeleteGoalUseCase`.
  - [ ] `ListGoalsUseCase` (active, completed).
  - [ ] `AdjustGoalProgressUseCase` (add or withdraw money to/from goal progress).

- [ ] **Backend REST Endpoints**
  - [ ] Create `GoalController` (`/api/v1/goals`):
    - `GET /api/v1/goals`
    - `POST /api/v1/goals`
    - `PUT /api/v1/goals/{id}`
    - `POST /api/v1/goals/{id}/deposit`
    - `DELETE /api/v1/goals/{id}`

- [ ] **Backend Tests**
  - [ ] Unit tests for goal progress percentage and automatic status transition to `COMPLETED` when `currentAmount >= targetAmount`.
  - [ ] Integration tests for database persistence and endpoints.

- [ ] **Frontend Goals Feature**
  - [ ] Create `src/features/goals/` types, services, and UI components.
  - [ ] Create Goals overview page with progress cards (visual progress circle or bar, remaining amount, estimated savings needed).
  - [ ] Create Create/Edit Goal modal.
  - [ ] Create Quick Deposit / Withdraw modal.
  - [ ] Add Active Goals card to Dashboard.

### Validation
- [ ] User can create a goal with a target amount.
- [ ] Depositing funds updates progress percentage and marks the goal completed when target is reached.
- [ ] Goal progress displays accurately on the dashboard.

### Done When
Financial goals can be created, updated, tracked, and displayed across the application.

---

## Phase 12 — Authentication, Authorization & User Data Isolation

> **Depends on:** Core business modules (Categories, Transactions, Dashboard, Budgets, Goals) working in single-user development mode.  
> **Rationale for timing:** Establishing domain models first ensures business rules remain clean and independent of security frameworks. Introducing security here transitions MilleStone into a secure, multi-user web application where every financial resource belongs to an owner.

### Architectural Strategy & Scope
* **User Data Isolation vs. Multi-Tenancy**: MilleStone requires user-level data ownership and isolation (`User A → User A data only`), not a complex multi-tenant organization architecture.
* **Authentication Strategy Evaluation**:
  - Compare **Session / HTTP-Only Secure Cookie** vs. **Token-based Authentication**.
  - Select the simplest, most secure approach appropriate for a React SPA + Spring Boot API.
  - Avoid storing sensitive tokens in `localStorage` / `sessionStorage` (XSS vulnerability).
  - Document the chosen approach in a dedicated Architecture Decision Record (ADR) before starting this phase.

```text
User
 ├── Transactions
 ├── Categories
 ├── Recurring Rules
 ├── Monthly Budgets
 └── Financial Goals
```

### Checklist

- [ ] **User Domain & Database Migration**
  - [ ] Create `V7__create_users_table.sql`:
    - `users` table: `id` (UUID), `email` (unique), `password_hash`, `full_name`, `created_at`, `enabled`.
  - [ ] Create `V8__add_user_id_to_all_entities.sql`:
    - Add `user_id` foreign key columns to `categories`, `transactions`, `recurring_rules`, `monthly_budgets`, and `financial_goals`.
    - Create composite indexes `(user_id, date)`, `(user_id, category_id)`, etc.
    - Set default categories to have `user_id = NULL` (system categories) or assign to owner.

- [ ] **Backend Spring Security Integration**
  - [ ] Add `spring-boot-starter-security` dependency.
  - [ ] Configure `PasswordEncoder` (`BCryptPasswordEncoder` with strong work factor).
  - [ ] Implement `SecurityFilterChain`:
    - Public endpoints: `/api/v1/auth/register`, `/api/v1/auth/login`, `/api/v1/system/status`, `/api/v1/system/ping`.
    - Protected endpoints: `/api/v1/**` (require authenticated principal).
    - Secure CORS configuration with `allowCredentials=true`.
    - CSRF protection configured for cookie-based authentication.

- [ ] **Authentication Use Cases & Endpoints**
  - [ ] Create `RegisterUserUseCase` (checks email uniqueness, hashes password, saves user).
  - [ ] Create `LoginUseCase` & `AuthService` (authenticates credentials, establishes secure session/cookie).
  - [ ] Create `GetCurrentUserUseCase` (`GET /api/v1/auth/me`).
  - [ ] Create `LogoutUseCase` (`POST /api/v1/auth/logout`).
  - [ ] Create `AuthController` (`/api/v1/auth`).

- [ ] **User Data Isolation Enforcement**
  - [ ] Implement security context helper (`AuthenticatedUserProvider`) to retrieve current user ID.
  - [ ] Enforce `user_id` ownership across **all** repository queries, use cases, and aggregations.
  - [ ] Guarantee that User A cannot read, update, or delete User B resources (return `404 Not Found` or `403 Forbidden`).

- [ ] **Backend Security & Isolation Tests**
  - [ ] Test registration, password hashing, and login failures (invalid credentials, unknown user).
  - [ ] **Critical Isolation Test**: User A attempting to access, update, or delete User B transactions, categories, budgets, or goals must be denied.
  - [ ] Test unauthenticated access to protected endpoints returns `401 Unauthorized`.

- [ ] **Frontend Authentication & Protected Routes**
  - [ ] Create `src/features/auth/` (types, `authService.ts`, `AuthContext`).
  - [ ] Create `AuthContext` / `useAuth` hook managing user session state (`user`, `isAuthenticated`, `isLoading`).
  - [ ] Create `LoginPage` and `RegisterPage` with form validation.
  - [ ] Create `ProtectedRoute` wrapper redirecting unauthenticated users to `/login`.
  - [ ] Add User Profile / Logout menu to application header.
  - [ ] Handle `401 Unauthorized` API responses globally by redirecting to login.

### Validation
- [ ] Unauthenticated requests to financial endpoints return `401 Unauthorized`.
- [ ] User can register, log in, view only their own financial data, and log out.
- [ ] Creating or modifying records as User A has zero visibility or effect on User B account.

### Done When
Authentication, authorization, and strict user data isolation are enforced across all endpoints and database queries, with secure session handling and protected frontend routes.

---

## Phase 13 — Financial Statement Imports (CSV / OFX)

> **Depends on:** Phase 7 (Transactions), Phase 6 (Categories), and Phase 12 (Authentication).

### Objective
Allow users to upload bank statement files (CSV or OFX), preview parsed transactions, detect potential duplicates, map categories, and confirm batch creation before persisting.

### Safe Import Pipeline
```text
Upload File (CSV/OFX) ──> Parse ──> Validate & Detect Duplicates ──> Preview & Confirmation ──> Persist (source = IMPORT)
```
> ⚠️ **Rule:** Never insert statement data directly into the database upon upload without user preview and explicit confirmation.

### Checklist

- [ ] **Backend Import Parser & Domain**
  - [ ] Create `ImportedTransaction` staging model (`rawDescription`, `date`, `amount`, `fitId`/hash, `suggestedType`).
  - [ ] Implement `CsvStatementParser` (supporting standard CSV formats: Date, Description, Amount).
  - [ ] Implement `OfxStatementParser` (supporting standard OFX `<STMTTRN>` tags).
  - [ ] Implement duplicate detection engine (matching existing transactions by date, amount, and description within a tolerance window).
  - [ ] Handle edge cases: invalid date/number formats, empty files, malformed headers, partial file problems.

- [ ] **Backend Import Endpoints**
  - [ ] Create `StatementImportController` (`/api/v1/imports`):
    - `POST /api/v1/imports/parse` (Multipart file upload -> returns preview list with duplicate flags).
    - `POST /api/v1/imports/confirm` (Receives user-confirmed list -> batch saves transactions with `source = IMPORT`).

- [ ] **Backend Tests**
  - [ ] Unit tests for CSV parser with various date/number formats and invalid lines.
  - [ ] Unit tests for OFX parser.
  - [ ] Unit tests for duplicate detection logic.
  - [ ] Integration test for batch confirmation persistence scoped to the authenticated user.

- [ ] **Frontend Import Feature**
  - [ ] Create `src/features/imports/` components.
  - [ ] Create `FileDropzone` modal/component for CSV/OFX files.
  - [ ] Create `ImportPreviewTable` allowing users to review, check/uncheck rows, and categorize items.
  - [ ] Confirm button executing bulk creation with success summary feedback.

### Validation
- [ ] Uploading a CSV/OFX statement parses rows accurately without crashing on formatting quirks.
- [ ] Suspected duplicate transactions are flagged in the preview modal.
- [ ] Confirmed transactions appear in the main Transaction list with `IMPORT` badge.

### Done When
Users can safely upload bank statements, review and categorize transactions in a preview screen, and import them without creating accidental duplicates.

---

## Phase 14 — API Scalability, Pagination & Advanced Filtering

> **Depends on:** Phase 7 and Phase 12 completion.

### Objective
Enhance API scalability and query capabilities for growing datasets through indexed pagination contracts, dynamic sorting, and multi-criteria date/category filtering.

### Checklist

- [ ] **Backend Pagination & Sorting**
  - [ ] Create reusable `PageResponse<T>` wrapper (content, pageNumber, pageSize, totalElements, totalPages, isLast).
  - [ ] Implement Spring `Pageable` in `TransactionRepositoryPort` and JPA adapters.
  - [ ] Support dynamic sorting (`sortBy=date,desc`, `sortBy=amount,asc`).

- [ ] **Advanced Filtering Specifications**
  - [ ] Add query filters: `startDate`, `endDate`, `categoryIds[]`, `types[]`, `minAmount`, `maxAmount`, `searchTerm`.
  - [ ] Optimize database indexes for multi-column filtered queries (e.g. `(user_id, date DESC)`).

- [ ] **Frontend Pagination & Filter Controls**
  - [ ] Integrate pagination controls (Previous, Next, Page Numbers) in `TransactionList`.
  - [ ] Add search input with debouncing for transaction descriptions.
  - [ ] Persist active filters in URL search params for bookmarkable filter states.

### Validation
- [ ] Querying `/api/v1/transactions?page=0&size=20&sortBy=date,desc` returns paginated metadata and a bounded slice of transactions.
- [ ] Frontend seamlessly pages through large transaction histories with fast response times.

### Done When
All high-volume list endpoints support efficient, indexed pagination, sorting, and multi-criteria filtering.

---

## Phase 15 — Observability, Logging & Diagnostics

> **Depends on:** Phase 12 completion.

### Objective
Equip the backend with structured logging, diagnostic health checks via Spring Boot Actuator, and auditability while strictly protecting sensitive user financial data.

### Privacy Rules
* **Never log**: Passwords, raw credentials, session tokens, full bank account details, or unmasked sensitive personal data.

### Checklist

- [ ] **Structured Logging Configuration**
  - [ ] Configure `logback-spring.xml` for structured console/JSON logging.
  - [ ] Define log levels (`INFO` for business events, `WARN`/`ERROR` for unexpected states, `DEBUG` for local development only).
  - [ ] Add Correlation/Request ID filter (`MDCFilter`) to track requests across log entries.

- [ ] **Spring Boot Actuator Setup**
  - [ ] Add `spring-boot-starter-actuator` to `pom.xml`.
  - [ ] Configure `application.yml` exposing safe endpoints (health, info, metrics).
  - [ ] Implement custom `DatabaseHealthIndicator` and disk space health check.

- [ ] **Audit Logging**
  - [ ] Log key security and business events (e.g. `User registered`, `User logged in`, `Batch import completed`).

### Validation
- [ ] `GET /actuator/health` returns `{"status":"UP"}`.
- [ ] Logs contain timestamps, log levels, request IDs, and logger names without leaking credentials.

### Done When
Application activity and errors are tracked with structured logs and health endpoints suitable for production monitoring.

---

## Phase 16 — Security Hardening & Pre-Production Review

> **Depends on:** All previous features implemented.

### Objective
Perform a rigorous security audit, vulnerability scanning, environment hardening, and configuration review before deploying to production.

### Checklist

- [ ] **Authentication & Session Security**
  - [ ] Verify passwords use strong BCrypt work factor (12+).
  - [ ] Verify cookies have `HttpOnly`, `Secure` in production, and `SameSite=Lax` or `Strict` flags enabled.
  - [ ] Configure rate limiting on sensitive endpoints (`/api/v1/auth/login`, `/api/v1/auth/register`) to prevent brute-force attacks.

- [ ] **HTTP Security Headers**
  - [ ] Configure Spring Security to emit standard security headers (CSP, X-Content-Type-Options, X-Frame-Options, HSTS, Referrer-Policy).

- [ ] **CORS & CSRF Hardening**
  - [ ] Restrict CORS in production strictly to the production frontend domain.
  - [ ] Enforce CSRF protection for cookie-authenticated mutating requests.

- [ ] **Dependency & Vulnerability Audit**
  - [ ] Run backend dependency vulnerability scan (`mvn dependency-check:check` / Dependabot).
  - [ ] Run frontend audit (`npm audit`).
  - [ ] Resolve any high or critical vulnerabilities.

- [ ] **Secret Management Verification**
  - [ ] Verify zero hardcoded secrets, passwords, or production keys exist in the repository.
  - [ ] Ensure all secrets are injected solely via environment variables.

### Validation
- [ ] Automated security scans pass with zero high/critical CVEs.
- [ ] `curl -I` against API verifies all security headers are present.

### Done When
The application passes security review, uses hardened headers and cookie configurations, and contains no committed secrets.

---

## Phase 17 — Containerization & CI/CD Pipeline

> **Depends on:** Phase 16 completion.

### Objective
Create production-grade container images for backend and frontend, and implement an automated Continuous Integration pipeline executing tests, lints, and builds on every pull request.

### Operational Progression
```text
Working Product ──> Quality & Tests ──> Security Hardening ──> Operational CI/CD ──> Production
```

### Checklist

- [ ] **Backend Dockerfile**
  - [ ] Create multi-stage `backend/Dockerfile` with non-root user and minimal JRE runtime image.
  - [ ] Expose port 8080.

- [ ] **Frontend Dockerfile & Hosting Strategy**
  - [ ] Create multi-stage `frontend/Dockerfile` with lightweight Nginx alpine server serving static `/dist` with SPA fallback routing.
  - [ ] Expose port 80/443.

- [ ] **Local Production Simulation (Compose)**
  - [ ] Create `docker-compose.prod.yml` to test full stack locally (postgres, backend, frontend).

- [ ] **CI Pipeline (e.g. GitHub Actions)**
  - [ ] Create `.github/workflows/ci.yml`:
    - **Trigger**: Pull requests to `main` and pushes to `main`.
    - **Backend Validation**: JDK 21, `./mvnw clean verify` (compilation, domain unit tests, MockMvc tests, Testcontainers integration tests).
    - **Frontend Validation**: Node.js, `npm ci`, `npm run lint`, `tsc --noEmit`, `npm test`, `npm run build`.
    - **Container Image Build**: Build backend and frontend images to verify Dockerfiles.

### Validation
- [ ] `docker compose -f docker-compose.prod.yml up --build` boots entire stack and functions cleanly.
- [ ] CI pipeline triggers on pull requests and reports green checkmarks on all test suites before merging.

### Done When
Standardized Docker images exist for all components and the CI pipeline automatically validates every pull request.

---

## Phase 18 — Production Deployment & Rollback Strategy

> **Depends on:** Phase 17 completion.

### Objective
Deploy MilleStone to a production environment (VPS, cloud container service, or managed PaaS), configure automated database backups, verify SSL/HTTPS, and establish a verified rollback plan.

### Deployment Topology (Provider-Neutral)
```text
Internet
   ↓ HTTPS (443)
Reverse Proxy / CDN / Nginx (SSL Termination)
   ├──> Static Assets (React SPA)
   └──> /api/* ──> Spring Boot Application (Port 8080)
                         ↓
                   PostgreSQL (Port 5432)
```

### Checklist

- [ ] **Production Infrastructure Provisioning (Provider-Neutral)**
  - [ ] Provision production PostgreSQL instance with daily automated backups.
  - [ ] Configure secure production environment variables on target hosting platform (VPS, Render, Railway, Fly.io, AWS, GCP, or Azure):
    - `SPRING_PROFILES_ACTIVE=prod`
    - `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
    - `CORS_ALLOWED_ORIGINS=https://app.yourdomain.com`
    - `SERVER_PORT=8080`

- [ ] **SSL / TLS Certificate Setup**
  - [ ] Configure SSL certificate (Let's Encrypt / Cloudflare / Managed PaaS SSL).
  - [ ] Enforce automatic HTTP -> HTTPS redirection.

- [ ] **Database Migration Execution & Strategy**
  - [ ] Flyway runs automatically on startup or via pre-deploy release command.
  - [ ] Verify baseline migrations apply cleanly to empty production database.

- [ ] **Production Health Verification (Smoke Test)**
  - [ ] Check `/actuator/health` endpoint returns `UP`.
  - [ ] Register initial user account and verify full end-to-end flow.

- [ ] **Backup & Disaster Recovery Plan**
  - [ ] Configure daily automated `pg_dump` backup with remote offsite storage (S3/R2/Backblaze).
  - [ ] Document database restore procedure in `docs/development/disaster-recovery.md`.

- [ ] **Rollback Procedure**
  - [ ] Document quick rollback steps (re-deploying previous Docker image tag / rolling back database migrations).

### Validation
- [ ] Application is accessible publicly over HTTPS with valid SSL certificates.
- [ ] End-to-end functionality verified in production with real database persistence.
- [ ] Database backup is verified by performing a test restore to a staging database.

> 🚀 **Milestone: MilleStone v1.0 Production Release**

### Done When
MilleStone is live in production, securely accessible over HTTPS, backed up automatically, and covered by a verified rollback plan.

---

## 🎯 Milestone Scope Distinction: First Usable Milestone vs. MilleStone v1.0

To maintain clarity throughout development, the project recognizes two distinct major milestones:

### 1. First Usable Milestone (Expected around Phase 7)
* **Goal**: Validate basic financial tracking capability end-to-end.
* **Included**:
  - Categories (seed + custom)
  - Full CRUD Transaction management (Income & Expense)
  - PostgreSQL persistence with exact decimal accuracy (`Money` / `numeric(19,2)`)
  - Usable React interface with validation and feedback states
* **Context**: Runs in single-user development mode; validates core domain value without security or operational overhead.

### 2. MilleStone v1.0 Production Release (Phase 18)
* **Goal**: Fully operational, multi-user, production-ready personal finance platform.
* **Included**:
  - User registration, login, logout, and strict user data isolation
  - Categories & Transaction tracking
  - Financial Dashboard with backend-calculated monthly totals and aggregations
  - Recurring Transactions with idempotent generation
  - Monthly Budgets with category spending limits and threshold alerts
  - Financial Goals with savings progress tracking
  - Responsive web interface (desktop and mobile)
  - Comprehensive automated tests across domain, application, persistence, and UI
  - Security hardening, HTTPS, and HTTP-Only session cookies
  - Containerized builds and automated CI/CD pipeline
  - Production deployment with automated database backups and rollback strategy
* **Post-v1.0 / Future Work**:
  - Financial Statement Imports (CSV / OFX) if not stabilized by v1.0
  - Open Finance / Direct banking APIs
  - Advanced analytics and multi-currency support

---

## Global Definition of Done (DoD)

A task, feature, or pull request is **NOT** done simply because code was written. A feature is considered **Done** only when:

- [ ] **Business Behavior Implemented**: All functional acceptance criteria for the vertical slice are satisfied.
- [ ] **Domain Rules Respected**: Financial calculations adhere to exact decimal precision (`BigDecimal` / `Money`) and domain invariants.
- [ ] **Backend Tests Passing**:
  - Pure domain unit tests with no Spring dependencies.
  - Use case tests with Mockito.
  - Repository integration tests with Testcontainers where SQL queries or migrations are involved.
  - Controller / WebMvc tests validating status codes, DTO validation, and error contracts.
- [ ] **Frontend Tests & Type Safety**: TypeScript compiles with zero errors (`tsc --noEmit`), and component/service tests pass.
- [ ] **UI States Handled**: Loading, Error, Empty, and Success feedback states are explicitly implemented.
- [ ] **Database Migrations Versioned**: Any schema changes are captured in an immutable, forward-compatible Flyway migration script (`V*__*.sql`).
- [ ] **API Consistency**: Adheres to REST naming conventions, uses dedicated DTOs, and integrates with the standard error response format.
- [ ] **Data Ownership Enforced**: In Phase 12+, all queries and mutations are strictly scoped to the authenticated user.
- [ ] **No Secrets Committed**: Configuration files, logs, and commits contain zero passwords, tokens, or sensitive credentials.
- [ ] **Code Cleanliness & Documentation**: Code follows Ports & Adapters separation, avoids unnecessary architectural ceremony, and updates relevant docs/ADRs.

---

## Pull Request Checklist

Developers must verify this checklist before opening or merging any Pull Request:

```markdown
### Pull Request Checklist

- [ ] **Scope**: Changes are focused on a single vertical slice or task (no giant multi-feature PRs).
- [ ] **Backend Build**: `./mvnw clean verify` passes with zero test failures.
- [ ] **Frontend Build**: `npm run lint`, `npm test`, and `npm run build` succeed without warnings/errors.
- [ ] **Database**: If schema changed, a new Flyway migration is included and tested with Testcontainers.
- [ ] **Domain Integrity**: Business rules live in the Domain/Application layer (not hidden in controllers or React components).
- [ ] **API Quality**: Standard DTOs, Bean Validation, and consistent error handling are applied.
- [ ] **Data Isolation**: User ownership (`user_id`) is enforced on all affected database queries (Phase 12+).
- [ ] **No Credentials**: No `.env`, secrets, or private credentials are included in the diff.
- [ ] **Documentation**: Roadmap checklist or architecture documents updated if applicable.
```

---

## Master Development Visual Summary

```text
Repository
    ↓
Spring Boot
    ↓
PostgreSQL + Flyway
    ↓
Backend Architecture
    ↓
React + TypeScript
    ↓
Frontend ↔ Backend ↔ Database
    ↓
Categories
    ↓
Transactions
    ↓
🎯 FIRST USABLE MILESTONE
    ↓
Dashboard
    ↓
Recurring Transactions
    ↓
Budgets
    ↓
Financial Goals
    ↓
Authentication + User Isolation
    ↓
Imports
    ↓
API Scalability
    ↓
Observability
    ↓
Security Hardening
    ↓
CI/CD
    ↓
Production Deployment
    ↓
🚀 MilleStone v1.0
```
