# MilleStone Development Roadmap

> **Master MVP Implementation Plan**  
> Project: **MilleStone** — Native Android Personal Finance Application  
> Mobile: **Kotlin / Jetpack Compose / Material 3**  
> Backend: **Java 21 / Spring Boot**  
> Database: **PostgreSQL / Flyway**  
> Architecture: **Android Client → HTTP/JSON API → Spring Boot → PostgreSQL**  
> Base Package: `io.github.paulogandolfi.milestone`

---

## 1. Current Product Direction

MilleStone is a **mobile-first native Android application**.

The previous React/Vite web frontend direction is no longer the target architecture and must not guide new implementation decisions.

The backend work already completed remains useful and continues to be the server-side foundation of the product.

The target architecture is:

```text
┌──────────────────────────────┐
│       MilleStone Android     │
│                              │
│ Kotlin                       │
│ Jetpack Compose              │
│ Material 3                   │
│ Navigation Compose           │
│ Coroutines                   │
└──────────────┬───────────────┘
               │
               │ HTTPS / JSON
               ▼
┌──────────────────────────────┐
│        Spring Boot API       │
│                              │
│ Java 21                      │
│ Spring Web MVC               │
│ Spring Data JPA              │
│ Bean Validation              │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│          PostgreSQL          │
│                              │
│ Flyway migrations            │
└──────────────────────────────┘
```

### Repository Direction

The long-term repository structure should reflect the mobile architecture:

```text
milestone/
├── android/                   # Native Android application
├── backend/                   # Spring Boot API
├── docs/
│   ├── architecture/
│   ├── development/
│   └── product/
├── docker-compose.yml         # Local backend/database infrastructure
├── .gitignore
└── README.md
```

The existing `frontend/` directory belongs to the superseded web direction. It should be treated as legacy and removed once the Android transition is safely established.

---

# 2. MVP Definition

The MVP is reached when MilleStone can be installed on an Android device and reliably supports the essential personal finance workflow from end to end.

A user must be able to:

1. Open the Android application.
2. View a simple financial home screen.
3. Create and view categories.
4. Register an income transaction.
5. Register an expense transaction.
6. View the transaction history.
7. Edit a transaction.
8. Delete a transaction.
9. See monthly income, expenses, and balance calculated from persisted data.
10. Close and reopen the app without losing server-side data.
11. Receive understandable loading, validation, empty, and error states.
12. Run the complete flow against the Spring Boot API and PostgreSQL database.

The MVP intentionally does **not** require:

- banking notification reading;
- automatic transaction detection;
- Open Finance / banking integrations;
- financial statement imports;
- recurring transactions;
- advanced budgets;
- financial goals;
- multi-device synchronization;
- complete offline-first synchronization;
- social login;
- advanced analytics;
- receipt scanning;
- widgets;
- premium plans;
- iOS support.

These capabilities can be introduced after the core financial workflow is stable.

---

# 3. MVP Roadmap Overview

```text
Phase 0  — Repository & Workspace Foundation
    ↓
Phase 1  — Spring Boot Backend Foundation
    ↓
Phase 2  — PostgreSQL & Flyway Foundation
    ↓
Phase 3  — Backend Architecture & Domain Foundation
    ↓
Phase 4  — Android Transition & Native App Foundation
    ↓
Phase 5  — Android ↔ Backend Connectivity
    ↓
Phase 6  — Categories End-to-End
    ↓
Phase 7  — Transactions End-to-End
    ↓
Phase 8  — Mobile Home / Financial Summary
    ↓
Phase 9  — MVP UX, Reliability & Error Handling
    ↓
Phase 10 — Android Build, Device Validation & MVP Release
    ↓
🎯 MILESTONE: FIRST USABLE ANDROID MVP
```

The phases must remain incremental. Each phase should leave the project in a working state before the next one begins.

---

# Phase 0 — Repository & Workspace Foundation

## Objective

Establish a clean repository, documentation structure, Git hygiene, and workspace conventions.

## Status

**Completed foundation.**

## Existing Work to Preserve

- Git repository and `.gitignore`.
- `backend/` workspace.
- `docs/` structure.
- Docker Compose infrastructure.
- README and development documentation.

## Mobile Transition Adjustment

The repository must stop treating the React/Vite application as the target client.

### Checklist

- [x] Repository initialized and organized.
- [x] Backend workspace created.
- [x] Documentation structure created.
- [x] Git hygiene established.
- [ ] Introduce `android/` as the official native client directory.
- [ ] Mark the old `frontend/` web client as superseded.
- [ ] Remove obsolete web-only files after the Android project is established and verified.

## Done When

The repository clearly communicates that the product is an Android application backed by the existing Spring Boot API.

---

# Phase 1 — Spring Boot Backend Foundation

## Objective

Provide a minimal, testable Spring Boot API that can serve the Android application.

## Status

**Foundation implemented.**

## Required Capabilities

- Java 21.
- Spring Boot.
- Maven Wrapper.
- Spring Web MVC.
- Bean Validation.
- Application configuration.
- Basic health endpoint.
- Automated context test.

## Validation

```bash
cd backend
./mvnw clean test
./mvnw spring-boot:run
```

The API should start successfully and expose a basic health/ping endpoint.

## Done When

The backend runs locally and can respond to HTTP requests independently of the Android application.

---

# Phase 2 — PostgreSQL & Flyway Foundation

## Objective

Provide reliable persistence with reproducible schema migrations.

## Status

**Foundation implemented.**

## Required Capabilities

- PostgreSQL for local development.
- Docker Compose service.
- Spring Data JPA.
- Flyway migrations.
- Hibernate schema validation.
- Testcontainers for database integration tests.

## Rules

- Never depend on Hibernate `create` or `update` for schema evolution.
- Database schema changes must go through Flyway.
- Production data must never depend on manually recreated tables.

## Validation

```bash
docker compose up -d
cd backend
./mvnw clean test
```

## Done When

The backend starts against PostgreSQL, Flyway applies migrations correctly, and integration tests use a real PostgreSQL test container.

---

# Phase 3 — Backend Architecture & Domain Foundation

## Objective

Create the minimum reusable architecture required for financial features without over-engineering the application.

## Status

**Foundation implemented.**

## Principles

- Prefer clear code over architecture for architecture's sake.
- Keep domain code independent from Spring and JPA where practical.
- Use exact monetary types (`BigDecimal`), never `float` or `double`.
- Centralize API error handling.
- Keep business rules in backend/domain code rather than duplicating them in the Android UI.

## Core Foundation

- `Money` value object.
- Domain exceptions.
- Standard API error payload.
- Global exception handling.
- Initial modular/hexagonal boundaries.

## Done When

The backend is ready to receive real financial features without first requiring another architecture rewrite.

---

# Phase 4 — Android Transition & Native App Foundation

## Objective

Create the official MilleStone Android client and establish the native mobile foundation.

This phase replaces the old **React / TypeScript / Vite frontend direction**.

## Technology Baseline

```text
Kotlin
Jetpack Compose
Material 3
AndroidX
Navigation Compose
Coroutines
Gradle
```

Additional libraries should only be introduced when justified by a real requirement.

## Android Project Identity

```text
Application name: MilleStone
Application ID: io.github.paulogandolfi.milestone
Namespace: io.github.paulogandolfi.milestone
Base package: io.github.paulogandolfi.milestone
```

## Checklist

- [ ] Create the Android project under `android/`.
- [ ] Configure Gradle and Android build files.
- [ ] Configure the application ID and namespace.
- [ ] Add Jetpack Compose and Material 3.
- [ ] Create the application entry activity.
- [ ] Create the base Compose theme.
- [ ] Define the initial green/white MilleStone visual identity.
- [ ] Add Navigation Compose.
- [ ] Create the initial navigation graph.
- [ ] Create placeholder screens:
  - [ ] Home.
  - [ ] Transactions.
  - [ ] Categories.
- [ ] Create reusable UI primitives only when actually needed.
- [ ] Establish package organization for screens, UI state, data access, and navigation.
- [ ] Verify the application runs in the Android emulator.
- [ ] Verify the application runs on a physical Android device when available.

## Suggested Initial Android Structure

```text
android/app/src/main/java/io/github/paulogandolfi/milestone/
├── MainActivity.kt
├── MilestoneApp.kt
├── navigation/
│   ├── MilestoneNavHost.kt
│   └── Routes.kt
├── core/
│   ├── network/
│   ├── ui/
│   └── model/
└── feature/
    ├── home/
    ├── transactions/
    └── categories/
```

Do not create empty repository/use-case/interface layers solely to satisfy a diagram. Introduce abstractions when a feature actually requires them.

## Validation

- Android project builds successfully.
- Emulator launches the application.
- Navigation between the initial screens works.
- No dependency on the React/Vite frontend exists.

## Done When

MilleStone is visibly a native Android application and can be launched and navigated on an Android device or emulator.

---

# Phase 5 — Android ↔ Backend Connectivity

## Objective

Prove the complete mobile-to-server communication path before implementing business features.

## Target Flow

```text
Compose Screen
    ↓
ViewModel / UI State
    ↓
Android Data Layer
    ↓
HTTP Client
    ↓
Spring Boot API
    ↓
PostgreSQL
```

## Checklist

### Backend

- [ ] Confirm a stable health/system endpoint is available.
- [ ] Configure local CORS/network behavior only if required by the chosen runtime setup.
- [ ] Keep API responses versioned under `/api/v1`.

### Android

- [ ] Add an HTTP client suitable for the application.
- [ ] Configure development API base URL.
- [ ] Add Android Internet permission.
- [ ] Handle emulator access to the local backend correctly.
- [ ] Create a small API service/client abstraction.
- [ ] Call the backend health endpoint from Android.
- [ ] Expose request state to Compose:
  - loading;
  - success;
  - failure.
- [ ] Display a simple connectivity result in a development/debug state.

## Important Rule

Do not start Categories or Transactions until the Android client can reliably communicate with the backend.

## Validation

```text
Android App → HTTP Request → Spring Boot → HTTP Response → Android UI
```

## Done When

A real Android screen successfully consumes data from the local Spring Boot API.

---

# Phase 6 — Categories End-to-End

## Objective

Implement the first complete business feature across Android, backend, and database.

## MVP Category Scope

A category should initially contain only what the MVP needs, for example:

```text
id
name
type: INCOME | EXPENSE
createdAt
updatedAt
```

Avoid category hierarchy, custom colors, icons, rules, or complex grouping during the MVP.

## Backend Checklist

- [ ] Create category domain model.
- [ ] Create persistence model/mapping.
- [ ] Add Flyway migration.
- [ ] Implement create category.
- [ ] Implement list categories.
- [ ] Implement update category.
- [ ] Implement delete category when allowed.
- [ ] Validate duplicate/invalid names.
- [ ] Expose REST endpoints.
- [ ] Add unit tests.
- [ ] Add persistence/integration tests.

## Android Checklist

- [ ] Create Categories screen.
- [ ] Load categories from API.
- [ ] Show loading state.
- [ ] Show empty state.
- [ ] Show error state.
- [ ] Add category creation form/dialog/screen.
- [ ] Validate required fields locally for usability.
- [ ] Submit creation to API.
- [ ] Refresh UI after mutation.
- [ ] Allow category editing.
- [ ] Allow category deletion where valid.
- [ ] Display backend validation errors in understandable language.

## Validation

Create a category on Android, restart/reload the application, and verify that the category still exists because it was persisted in PostgreSQL.

## Done When

Categories work end to end and provide the classification foundation required by transactions.

---

# Phase 7 — Transactions End-to-End

## Objective

Deliver the core MilleStone capability: registering income and expenses.

This is the most important MVP business phase.

## MVP Transaction Model

A transaction should support the minimum useful information:

```text
id
kind: INCOME | EXPENSE
amount
description
categoryId
transactionDate
createdAt
updatedAt
```

Optional fields should be introduced only when a real MVP requirement demands them.

## Backend Checklist

- [ ] Create transaction domain model.
- [ ] Use `BigDecimal` / `Money` for monetary values.
- [ ] Add Flyway migration.
- [ ] Persist category relationship.
- [ ] Implement create transaction.
- [ ] Implement list transactions.
- [ ] Implement get transaction by ID if required by editing flow.
- [ ] Implement update transaction.
- [ ] Implement delete transaction.
- [ ] Validate positive amount.
- [ ] Validate transaction type.
- [ ] Validate referenced category.
- [ ] Validate transaction date.
- [ ] Define deterministic ordering, newest first.
- [ ] Add unit and integration tests.

## Android Checklist

### Transaction List

- [ ] Load transactions from API.
- [ ] Display income and expenses clearly.
- [ ] Display description, category, amount, and date.
- [ ] Provide empty/loading/error states.
- [ ] Refresh after mutations.

### Add Transaction

- [ ] Add primary action for new transaction.
- [ ] Choose Income or Expense.
- [ ] Enter amount.
- [ ] Enter description.
- [ ] Select category.
- [ ] Select transaction date.
- [ ] Submit to backend.
- [ ] Show validation errors without losing entered data.
- [ ] Return to the list after success.

### Edit Transaction

- [ ] Open an existing transaction.
- [ ] Populate current values.
- [ ] Save changes through API.
- [ ] Reflect updated data immediately.

### Delete Transaction

- [ ] Require user confirmation.
- [ ] Delete through API.
- [ ] Remove item from current UI after success.

## Validation Scenario

Execute this complete flow on Android:

```text
Create category "Salary"
        ↓
Register income R$ 5,000.00
        ↓
Create category "Food"
        ↓
Register expense R$ 250.00
        ↓
Edit expense to R$ 275.00
        ↓
Restart application
        ↓
Verify both transactions remain persisted
        ↓
Delete one transaction
        ↓
Verify database/UI consistency
```

## Done When

A user can reliably manage their real income and expenses from the Android application.

---

# Phase 8 — Mobile Home / Financial Summary

## Objective

Transform persisted transactions into an immediately useful financial overview.

## MVP Home Scope

The Home screen should answer, at minimum:

- How much did I earn this month?
- How much did I spend this month?
- What is my balance for the month?
- What were my latest transactions?

## Initial Home Components

```text
Current Month

Income
R$ X

Expenses
R$ Y

Balance
R$ Z

Recent Transactions
- ...
```

## Backend Checklist

- [ ] Define monthly summary API contract.
- [ ] Calculate total income for selected/current month.
- [ ] Calculate total expenses for selected/current month.
- [ ] Calculate balance.
- [ ] Return recent transactions or reuse a transaction endpoint efficiently.
- [ ] Add tests for financial aggregation.
- [ ] Ensure calculations use exact decimal values.

## Android Checklist

- [ ] Build Home screen.
- [ ] Load financial summary.
- [ ] Display month context.
- [ ] Display income.
- [ ] Display expenses.
- [ ] Display balance.
- [ ] Display recent transactions.
- [ ] Handle empty month gracefully.
- [ ] Refresh Home after a transaction is created/updated/deleted.
- [ ] Format BRL currency consistently.

## Done When

Opening MilleStone immediately provides a useful picture of the user's current monthly finances.

---

# Phase 9 — MVP UX, Reliability & Error Handling

## Objective

Make the application reliable enough for daily personal use rather than merely technically functional.

## Android UX Checklist

- [ ] Consistent navigation behavior.
- [ ] Back button works correctly.
- [ ] Keyboard does not break forms.
- [ ] Monetary input is usable on mobile.
- [ ] Forms preserve state during ordinary recomposition/navigation cases.
- [ ] Prevent accidental duplicate submissions.
- [ ] Disable actions while a mutation is in progress when necessary.
- [ ] Show progress feedback.
- [ ] Show understandable API/network errors.
- [ ] Show confirmation for destructive actions.
- [ ] Add meaningful empty states.
- [ ] Use consistent spacing, typography, and Material 3 components.
- [ ] Verify light theme.
- [ ] Define dark-theme behavior or explicitly defer it.
- [ ] Add basic accessibility content descriptions where appropriate.

## Backend Reliability Checklist

- [ ] Consistent API validation responses.
- [ ] No stack traces or internal implementation details exposed to clients.
- [ ] Database constraints align with domain rules.
- [ ] Transaction/category operations are transactional where needed.
- [ ] Integration tests cover primary happy paths and key invalid flows.

## Network Checklist

- [ ] Development base URL is not hardcoded throughout the codebase.
- [ ] Timeouts are configured reasonably.
- [ ] No credentials or secrets are stored in source code.
- [ ] Android handles unavailable backend cleanly.

## Done When

Common failures do not leave the Android application in a broken, confusing, or inconsistent state.

---

# Phase 10 — Android Build, Device Validation & MVP Release

## Objective

Produce a real installable Android build and validate the complete MVP outside the development preview environment.

## Build Checklist

- [ ] Confirm debug build succeeds from command line.
- [ ] Configure application version name and version code.
- [ ] Configure launcher icon and application name.
- [ ] Remove temporary debug UI.
- [ ] Review Android permissions.
- [ ] Verify no unnecessary permissions are requested.
- [ ] Create a release build configuration.
- [ ] Define secure signing strategy without committing signing secrets.
- [ ] Generate an installable APK/AAB as appropriate.

## Physical Device Validation

Test the complete workflow on a real Android device:

- [ ] Install the application.
- [ ] Launch from Android launcher.
- [ ] Open Home.
- [ ] Create category.
- [ ] Create income.
- [ ] Create expense.
- [ ] Edit transaction.
- [ ] Delete transaction.
- [ ] Validate monthly totals.
- [ ] Close application completely.
- [ ] Reopen application.
- [ ] Verify persisted data remains correct.
- [ ] Validate network failure behavior.
- [ ] Validate slow request/loading behavior.
- [ ] Validate common screen sizes available during development.

## Backend Deployment Requirement

For the MVP to work outside the local development network, the Android application eventually needs access to a deployed backend over HTTPS.

Before distributing the MVP beyond local development:

- [ ] Deploy Spring Boot API to a controlled environment.
- [ ] Deploy PostgreSQL with backup strategy.
- [ ] Configure environment-specific secrets.
- [ ] Use HTTPS.
- [ ] Configure Android production API URL.
- [ ] Add basic operational logging.
- [ ] Verify Flyway migrations during deployment.

## Done When

A build installed on a real Android device completes the entire MVP financial workflow against persistent backend storage.

---

# 4. MVP Acceptance Criteria

MilleStone reaches the **First Usable Android MVP** only when all of the following are true:

### Product

- [ ] Native Android app launches reliably.
- [ ] User can manage categories.
- [ ] User can create income transactions.
- [ ] User can create expense transactions.
- [ ] User can list, edit, and delete transactions.
- [ ] Home shows correct monthly income.
- [ ] Home shows correct monthly expenses.
- [ ] Home shows correct monthly balance.

### Persistence

- [ ] Data is persisted in PostgreSQL.
- [ ] Data survives Android application restarts.
- [ ] Database schema is controlled by Flyway.

### Mobile Experience

- [ ] Core flows work on an Android emulator.
- [ ] Core flows work on a physical Android device.
- [ ] Loading, empty, validation, and error states exist.
- [ ] Forms are comfortable enough for real mobile use.

### Backend

- [ ] API validation is consistent.
- [ ] Main financial flows have automated tests.
- [ ] Monetary calculations avoid floating-point types.
- [ ] Backend can be deployed independently of the Android application.

### Codebase

- [ ] The React/Vite frontend is no longer part of the active architecture.
- [ ] Documentation describes Android as the official client.
- [ ] No obsolete web assumption is required to run the MVP.

When these criteria are satisfied:

```text
🎯 MilleStone Android MVP = COMPLETE
```

---

# 5. What Comes After the MVP

The following roadmap starts only after the core Android MVP is stable enough for daily use.

## Post-MVP 1 — Authentication & User Isolation

- User accounts.
- Secure authentication.
- Token lifecycle.
- Per-user data isolation.
- Secure credential storage on Android.
- Optional biometric unlock.

## Post-MVP 2 — Recurring Transactions

- Salary.
- Rent.
- Utilities.
- Subscriptions.
- Loans.
- Recurrence rules and projected expenses.

## Post-MVP 3 — Budgets

- Monthly total budget.
- Budget utilization.
- Category budgets if real product usage justifies them.

## Post-MVP 4 — Financial Goals

- Goal value.
- Current saved value.
- Progress.
- Contributions.

## Post-MVP 5 — Android Notification Assistance

Potential future flow:

```text
Bank Notification
        ↓
Android Notification Access
        ↓
Local Parser
        ↓
Suggested Transaction
        ↓
User Confirmation
        ↓
Backend API
```

Important rules:

- Notification access must be optional.
- The user must remain in control.
- Automatic parsing must not silently create incorrect financial records.
- Privacy and Android platform restrictions must be reviewed before implementation.

## Post-MVP 6 — Imports & Integrations

- CSV import.
- OFX import.
- Future Open Finance / institution integrations where practical.

## Post-MVP 7 — Offline Experience

Only introduce a full offline/synchronization architecture after the application has proven that it genuinely needs it.

Potential technologies include:

- Room.
- WorkManager.
- conflict resolution strategy.
- background synchronization.

## Post-MVP 8 — Advanced Mobile Capabilities

Potential future features:

- widgets;
- shortcuts;
- reminders;
- receipt/document camera capture;
- richer charts;
- export;
- notifications;
- dark theme refinements.

---

# 6. Development Rules for Every Phase

Every implementation phase should follow these rules.

## Rule 1 — Keep the Project Runnable

Do not merge a phase that leaves the main branch intentionally broken.

## Rule 2 — One Vertical Capability at a Time

Prefer completing one real user flow across Android, backend, and database instead of creating many disconnected abstractions.

## Rule 3 — Database Changes Use Flyway

Never manually rely on Hibernate schema creation for application evolution.

## Rule 4 — Financial Values Must Be Exact

Use `BigDecimal` / domain monetary types in backend financial calculations.

## Rule 5 — Mobile UX Matters

A feature is not complete just because the API works. It must also be usable from the Android application.

## Rule 6 — Avoid Premature Architecture

Do not create interfaces, layers, modules, caches, background jobs, synchronization engines, or generic frameworks until a concrete requirement demands them.

## Rule 7 — Tests Follow Business Risk

Prioritize tests for:

- monetary calculations;
- persistence;
- validation;
- transaction mutations;
- financial aggregations;
- API contracts;
- important Android state behavior.

## Rule 8 — MVP Scope Is Protected

When a new idea appears, ask:

> Is this necessary for a user to register, understand, and maintain their basic finances in the first usable Android version?

If not, place it in the post-MVP backlog rather than delaying the MVP.

---

# 7. Recommended Next Step

The immediate next implementation step is:

```text
Phase 4 — Android Transition & Native App Foundation
```

The goal is **not** to recreate every web screen on Android.

The goal is to establish a clean native Android application shell, connect it to the backend in Phase 5, and then deliver the MVP through small end-to-end financial slices.

The first meaningful mobile sequence should therefore be:

```text
Android Foundation
      ↓
Backend Connectivity
      ↓
Categories
      ↓
Transactions
      ↓
Financial Home
      ↓
MVP Hardening
      ↓
Installable Android MVP
```

That sequence is the shortest path from the current technical foundation to a MilleStone application that can actually be used on a phone every day.
