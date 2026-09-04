# MilleStone

MilleStone is a **personal finance management application** focused on simplicity, privacy, maintainability, and incremental evolution.

The project is being built as a **native Android application** backed by a dedicated **Java / Spring Boot API** and **PostgreSQL** database.

The goal is simple:

> Make personal finance easier to understand and manage without turning financial tracking into another daily burden.

---

## 📌 Project Status

> 🚧 **Under development**

### Current Version

`0.1.0-SNAPSHOT`

### Current Direction

MilleStone is now a **mobile-first Android project**.

The first technical phases established the backend, database, and domain foundations. Starting with **Phase 4**, development moves to the native Android client.

```text
Android App
Kotlin + Jetpack Compose
        │
        │ HTTP / JSON
        ▼
Backend API
Java + Spring Boot
        │
        ▼
PostgreSQL
```

The previous React/Vite frontend direction is no longer the target architecture.

---

# 🎯 Purpose

MilleStone should help users quickly understand their financial situation and answer questions such as:

- How much did I earn this month?
- How much have I spent?
- How much money is currently available?
- What are my biggest expenses?
- How much do I still have in upcoming expenses?
- Am I staying within my budget?
- How are my financial goals progressing?
- How are my investments performing?

The product should prioritize **clarity and usefulness over unnecessary complexity**.

The mobile experience is especially important because financial transactions happen throughout the day. Recording an expense should be fast enough to do immediately after a purchase instead of becoming a task the user postpones.

---

# 🚀 Product Vision

The first usable version of MilleStone will focus on the essential personal finance workflow:

```text
Income
   +
Expenses
   ↓
Transactions
   ↓
Financial Overview
   ↓
Budget
   ↓
Goals
```

Investments and Open Finance integrations are planned as later product areas, after the core personal-finance workflow is stable.

The initial objective is not to create a complete banking platform.

The objective is to create a reliable personal finance application that works well for everyday use and can evolve incrementally.

---

# 💰 Income

Users will be able to register financial income such as:

- salary;
- payments;
- received transfers;
- freelance income;
- bonuses;
- other income.

Example:

```text
Salary

Amount:
R$ 5,000.00

Category:
Salary

Date:
05/08/2026
```

---

# 💸 Expenses

Users will be able to register expenses such as:

- food;
- transportation;
- housing;
- entertainment;
- healthcare;
- shopping;
- subscriptions;
- education;
- other expenses.

Example:

```text
Supermarket

Amount:
R$ 327.40

Category:
Food

Date:
08/08/2026
```

---

# 🗂️ Categories

Transactions can be organized into categories.

Initial examples:

```text
Income
Salary
Food
Transportation
Housing
Entertainment
Healthcare
Education
Subscriptions
Shopping
Other
```

The first implementation should remain intentionally simple. Advanced category hierarchies, icons, colors, and other customization should only be added when there is a real product need.

---

# 🔁 Recurring Transactions

Users will eventually be able to register recurring income and expenses.

Examples:

```text
Salary
Rent
Internet
Electricity
Gym
Streaming Services
Mobile Plan
Loans
```

Recurring rules may automatically create or project expected transactions for future periods.

Example:

```text
Rent

Amount:
R$ 1,500.00

Frequency:
Monthly

Due day:
10
```

---

# 📊 Dashboard

The dashboard should provide a fast overview of the user's financial situation.

Planned indicators include:

- current balance;
- projected balance;
- monthly income;
- monthly expenses;
- budget usage;
- expenses by category;
- upcoming expenses;
- recent transactions;
- financial goal progress.

Example:

```text
August 2026

Income
R$ 5,500.00

Expenses
R$ 3,120.00

Balance
R$ 2,380.00

Projected Balance
R$ 1,940.00
```

---

# 💳 Monthly Budget

Users will be able to define monthly spending limits.

Example:

```text
Monthly Budget

R$ 3,000.00

Used
R$ 1,840.00

Remaining
R$ 1,160.00

Usage
61%
```

Future versions may also support budgets per category.

---

# 🎯 Financial Goals

Users will be able to create and track financial objectives.

Example:

```text
Emergency Fund

Goal:
R$ 10,000.00

Current:
R$ 3,500.00

Progress:
35%
```

Possible goals include emergency funds, travel, debt payments, vehicles, property, or personal purchases.

---

# 📈 Investments

A future MilleStone version will include a dedicated **Investments** area so personal cash flow and investment positions can be viewed in the same application without coupling the investment domain to the initial MVP.

Planned capabilities may include:

- register and track investment positions;
- stocks, FIIs, ETFs, and other asset classes as the product evolves;
- quantity and average purchase price;
- invested amount;
- current market value;
- unrealized gain or loss;
- portfolio allocation;
- dividends and other investment income;
- historical portfolio evolution;
- market price updates through an external market-data provider.

Market data should be accessed through the backend using a provider-independent port instead of coupling the domain directly to a specific external API.

Conceptual direction:

```text
Investments Use Cases
        ↓
MarketDataPort
        ↓
Provider Adapter
        ├── Yahoo Finance-compatible source
        ├── Brapi
        └── Other provider
```

The initial preference is to evaluate free or open-source-compatible data sources before introducing a paid dependency. The historical open-source Brapi project and Yahoo Finance-compatible sources are references for this research.

Provider-specific concerns such as authentication, rate limits, response formats, retries, and fallback strategies must remain inside infrastructure adapters.

Investments should be introduced only after the core transaction, dashboard, budget, and financial-goal workflows are stable.

---

# 🏦 Open Finance Integration

A future MilleStone version may integrate with the **Open Finance Brasil** ecosystem as an advanced feature focused on financial automation, technical study, and portfolio development.

This integration is intentionally outside the MVP. The initial implementation should favor official specifications, sandbox environments, and simulated providers before any attempt to consume protected production banking data.

Planned capabilities may include:

- discover supported financial institutions;
- model and track user consent;
- study and implement OAuth 2.0 / OpenID Connect / FAPI concepts used by financial APIs;
- import bank accounts;
- synchronize account balances;
- synchronize bank transactions;
- import credit-card information and transactions where supported;
- map imported transactions into MilleStone categories;
- reconcile imported transactions with existing records;
- periodically synchronize authorized financial data;
- handle consent expiration, revocation, retries, and provider failures.

Open Finance access should be isolated behind a provider-independent port so application and domain rules do not depend directly on a specific institution or external API.

Conceptual direction:

```text
Open Finance Use Cases
        ↓
OpenFinancePort
        ↓
Provider Adapter
        ├── Sandbox / Mock Provider
        └── Open Finance-compatible Provider
```

Provider-specific concerns such as authorization flows, access tokens, consent identifiers, scopes, mTLS, certificates, retries, and external response formats must remain inside infrastructure adapters.

Production integration with protected banking data depends on the technical, security, certification, and participation requirements of the Open Finance ecosystem. MilleStone should therefore treat sandbox and simulated integrations as the primary learning path unless production access becomes appropriate later.

This feature is especially valuable to the project as a portfolio exercise because it introduces real-world integration concerns such as consent management, security protocols, idempotent synchronization, external API failures, and sensitive financial-data handling.

---

# 🤖 Financial Automation

Mobile automation is one of the long-term differentiators planned for MilleStone.

A future Android version may optionally detect supported banking notifications and suggest a transaction to the user.

Possible flow:

```text
Bank Notification
        ↓
Android Notification Access
        ↓
Transaction Parser
        ↓
Transaction Suggestion
        ↓
User Review / Confirmation
        ↓
MilleStone API
```

Other possible automation strategies include:

```text
Bank Statement Import
        ↓
Transaction Parser
        ↓
MilleStone
```

```text
Financial Institution API
        ↓
Integration Adapter
        ↓
Transaction
```

Automation must remain optional and should only be introduced after the core manual financial workflow is stable.

---

# 📱 Why Native Android

MilleStone is designed around frequent, short interactions throughout the day.

A native Android application enables a better path for features such as:

- quick transaction registration;
- local notifications and reminders;
- biometric authentication;
- Android notification access;
- offline support and synchronization;
- background work with WorkManager;
- future widgets and shortcuts;
- camera access for receipts or documents;
- deeper integration with the Android platform.

The Android client will be built with **Kotlin and Jetpack Compose**.

---

# 🛠️ Tech Stack

## Android

```text
Kotlin
Jetpack Compose
Material 3
AndroidX
Navigation Compose
Coroutines
```

Additional Android libraries should only be introduced when they solve an actual product requirement.

Possible future additions:

```text
Retrofit / OkHttp
Room
WorkManager
Hilt
DataStore
Biometric
```

Architectural choices should remain proportional to the size of the project.

---

## Backend

```text
Java 21
Spring Boot
Spring Web MVC
Spring Data JPA
Bean Validation
Maven
```

The backend exposes the application capabilities through HTTP APIs and remains responsible for server-side business workflows, persistence coordination, and future integrations.

---

## Database

```text
PostgreSQL
Flyway
```

Database schema evolution is managed through versioned migrations.

Development infrastructure uses Docker Compose where appropriate.

---

## Testing

### Backend

```text
JUnit
Mockito
Spring Boot Test
Testcontainers
```

### Android

Planned testing strategy:

```text
JUnit
AndroidX Test
Compose UI Testing
```

End-to-end coverage should be introduced around the most important user flows as the application becomes usable.

---

# 🏗️ Architecture

The backend follows an architecture inspired by:

> **Clean Architecture / Hexagonal Architecture / Ports and Adapters**

The goal is to keep important business rules independent from framework and infrastructure details whenever practical.

Simplified system view:

```text
┌──────────────────────────────────────────┐
│               ANDROID APP                │
│                                          │
│        Kotlin + Jetpack Compose          │
│                                          │
│  Screens                                 │
│  ViewModels                              │
│  Android Use Cases                       │
│  API Clients                             │
└────────────────────┬─────────────────────┘
                     │
                     │ HTTP / JSON
                     ▼
┌──────────────────────────────────────────┐
│              API / ADAPTERS              │
│                                          │
│  REST Controllers                        │
│  Request / Response DTOs                 │
│  Exception Handlers                      │
└────────────────────┬─────────────────────┘
                     │
                     ▼
┌──────────────────────────────────────────┐
│              APPLICATION                 │
│                                          │
│  Use Cases                               │
│  Application Services                    │
│  Input / Output Ports                    │
└────────────────────┬─────────────────────┘
                     │
                     ▼
┌──────────────────────────────────────────┐
│                 DOMAIN                   │
│                                          │
│  Entities                                │
│  Value Objects                           │
│  Business Rules                          │
│  Domain Services                         │
└────────────────────▲─────────────────────┘
                     │
                     │ implements ports
                     │
┌────────────────────┴─────────────────────┐
│          INFRASTRUCTURE / ADAPTERS       │
│                                          │
│  PostgreSQL                              │
│  JPA                                     │
│  External APIs                           │
│  Import / Export                         │
└──────────────────────────────────────────┘
```

An important backend rule is that the core domain should not depend directly on infrastructure concerns such as:

```text
Spring
JPA
PostgreSQL
HTTP
Android
```

Architecture should serve the product instead of becoming a source of unnecessary ceremony.

---

# 🌐 Application Flow

A typical operation should follow a flow similar to:

```text
Jetpack Compose Screen
        ↓
ViewModel
        ↓
Android Use Case
        ↓
API Client
        ↓
HTTP / JSON
        ↓
REST Controller
        ↓
Backend Use Case
        ↓
Domain
        ↓
Repository Port
        ↓
Persistence Adapter
        ↓
PostgreSQL
```

Response:

```text
PostgreSQL
        ↓
Persistence Adapter
        ↓
Backend Application
        ↓
REST Controller
        ↓
JSON
        ↓
Android API Client
        ↓
ViewModel
        ↓
Compose UI
```

The Android application must **never access PostgreSQL directly**.

---

# 📦 Repository Structure

Current repository structure:

```text
milestone/
│
├── backend/
├── frontend/          # Previous web direction; to be retired during mobile migration
├── docs/
├── docker-compose.yml
├── .gitignore
└── README.md
```

Target structure after the Android migration:

```text
milestone/
│
├── backend/
│   ├── src/
│   └── pom.xml
│
├── android/
│   ├── app/
│   ├── gradle/
│   ├── build.gradle.kts
│   └── settings.gradle.kts
│
├── docs/
├── decisions/
├── docker-compose.yml
├── .gitignore
└── README.md
```

The existing frontend directory should only be removed or replaced as part of the Android migration work, keeping repository history explicit and controlled.

---

# ☕ Backend Structure

Base package:

```text
io.github.paulogandolfi.milestone
```

The backend organization follows the project architecture while preferring clarity over ceremony.

Conceptually:

```text
io.github.paulogandolfi.milestone
│
├── domain
├── application
├── adapter
│   ├── in
│   └── out
└── config
```

As the application grows, organization by business feature may be preferred where it improves maintainability.

Possible feature areas:

```text
transaction
category
budget
goal
investment
openfinance
shared
```

---

# 🤖 Android Structure

The Android project will prefer a feature-oriented structure instead of one large package organized only by technical type.

Initial direction:

```text
io.github.paulogandolfi.milestone
│
├── app
│   ├── navigation
│   └── theme
│
├── core
│   ├── network
│   ├── model
│   └── ui
│
├── feature
│   ├── dashboard
│   ├── transactions
│   ├── categories
│   ├── budgets
│   ├── goals
│   ├── investments
│   └── openfinance
│
└── MainActivity.kt
```

The exact structure should evolve with the application instead of being over-engineered up front.

---

# 🧠 Initial Domain Model

Main planned concepts include:

```text
Transaction
Category
RecurringRule
MonthlyBudget
FinancialGoal
User
```

Investment and Open Finance-specific domain concepts should be introduced later, when those features enter active development, instead of prematurely fixing those models now.

Not every planned concept needs to be implemented immediately.

The model should evolve according to actual product requirements.

---

# 💵 Transaction

A transaction represents a financial event.

Types:

```text
INCOME
EXPENSE
```

Possible sources:

```text
MANUAL
IMPORT
INTEGRATION
AUTOMATION
```

The first usable version will primarily use:

```text
MANUAL
```

Possible representation:

```text
Transaction

id
description
amount
type
category
transactionDate
source
createdAt
updatedAt
```

Exact domain and persistence models should be defined during implementation rather than prematurely fixed in documentation.

---

# 🔐 Privacy and Security

Financial information is sensitive and must be handled carefully.

MilleStone should follow these principles:

- financial data should never be exposed unnecessarily;
- database access must remain exclusively behind the backend;
- credentials and secrets must never be stored directly in the Android client;
- the app communicates with the backend through defined APIs;
- sensitive configuration must use appropriate secret/configuration mechanisms;
- logs must avoid exposing sensitive financial information;
- external integrations should be explicitly enabled;
- notification access must be optional and transparent;
- Open Finance access must depend on explicit, traceable, and revocable consent;
- authentication must be implemented before exposing personal financial data publicly;
- biometric authentication may be used as an additional device-level protection later.

During the initial development phase, the backend may run only in a local development environment.

---

# 🗺️ Development Roadmap

The project is developed incrementally. Each phase should leave the repository in a working, understandable state.

## Phase 0 — Repository & Workspace Preparation ✅

Repository organization and workspace foundation.

Main goals:

- establish the repository root;
- create backend/documentation areas;
- configure `.gitignore`;
- prepare the development roadmap;
- remove obsolete project structure.

---

## Phase 1 — Spring Boot Foundation ✅

Backend technical foundation.

Main goals:

- Java 21;
- Spring Boot;
- Maven Wrapper;
- Spring Web MVC;
- basic health endpoint;
- initial automated tests.

---

## Phase 2 — Database Foundation ✅

PostgreSQL and database evolution foundation.

Main goals:

- PostgreSQL development environment;
- Docker Compose;
- Flyway migrations;
- persistence configuration;
- integration testing foundation with Testcontainers.

---

## Phase 3 — Backend Architecture & Domain Foundation ✅

Establish the backend architectural direction and first shared domain concepts.

Main goals:

- Clean / Hexagonal Architecture principles;
- domain independence from infrastructure;
- application ports and adapters;
- shared value objects such as `Money`;
- global error handling;
- architecture that prefers clarity over ceremony.

---

## Phase 4 — Android Foundation 🚧

Replace the previous web-client direction with the native Android foundation.

Main goals:

- create the Android project;
- Kotlin;
- Jetpack Compose;
- Material 3;
- application theme;
- navigation foundation;
- feature-oriented package structure;
- API client foundation;
- Android unit/UI testing foundation;
- retire the previous React/Vite frontend cleanly.

Expected result:

```text
Android App
   ↓
HTTP / JSON
   ↓
Spring Boot
   ↓
PostgreSQL
```

---

## Phase 5 — First Full Integration

Create the first vertical communication path between mobile, backend, and database.

Expected flow:

```text
Jetpack Compose
      ↓
Android API Client
      ↓
Spring Boot
      ↓
PostgreSQL
```

The objective is to prove the complete technical path before expanding business features.

---

## Phase 6 — Categories

Implement the minimum category workflow.

Main goals:

- default categories;
- custom categories;
- list categories;
- create categories;
- basic validation;
- Android integration.

Avoid premature category hierarchies, colors, icons, or advanced configuration.

---

## Phase 7 — Transaction Management 🎯

This phase represents the **first genuinely usable MilleStone milestone**.

Main goals:

- create income;
- create expenses;
- list transactions;
- edit transactions;
- delete transactions;
- select categories;
- persist data in PostgreSQL;
- display validation and API errors properly in Android;
- preserve data after application restart and refresh.

Expected user flow:

```text
New Transaction
       ↓
Android Form
       ↓
REST API
       ↓
Use Case
       ↓
Repository
       ↓
PostgreSQL
       ↓
Transaction List
```

---

## Phase 8 — Dashboard

Planned scope:

- monthly balance;
- monthly income;
- monthly expenses;
- expenses by category;
- recent transactions;
- projected balance.

---

## Phase 9 — Budgets

Planned scope:

- monthly budget;
- remaining budget;
- budget usage;
- category budgets when justified;
- budget alerts.

---

## Phase 10 — Financial Goals

Planned scope:

- create financial goals;
- update progress;
- complete goals;
- display progress in the dashboard.

---

## Phase 11 — Recurring Transactions

Planned scope:

- recurring expenses;
- recurring income;
- recurrence rules;
- upcoming transactions;
- projected balance integration.

---

## Phase 12 — Notifications & Reminders

Use native Android capabilities for useful financial reminders.

Possible scope:

- upcoming expense reminders;
- budget alerts;
- goal reminders;
- configurable notification preferences.

---

## Phase 13 — Bank Notification Detection

Evaluate optional Android notification access for financial transaction suggestions.

Principles:

- explicit user permission;
- no silent financial actions;
- parse only supported notification patterns;
- user review before persistence;
- clear confidence/fallback behavior;
- avoid storing unnecessary notification contents.

Expected concept:

```text
Bank Notification
       ↓
Parser
       ↓
Transaction Suggestion
       ↓
User Confirmation
       ↓
Persist Transaction
```

---

## Phase 14 — Offline & Synchronization

Evaluate local persistence and synchronization once the core online workflow is stable.

Possible technologies:

```text
Room
WorkManager
Connectivity APIs
```

Offline behavior should be designed deliberately to avoid data duplication and conflict issues.

---

## Phase 15 — Authentication & Biometrics

Authentication becomes mandatory before the backend is publicly exposed.

Possible scope:

- user model;
- registration;
- login;
- password hashing;
- authorization;
- user data isolation;
- token/session strategy;
- Android secure credential handling;
- optional biometric unlock;
- security tests.

Technology decisions should be made when this phase begins rather than prematurely.

---

## Phase 16 — Investments & Market Data

Introduce the investment portfolio only after the core financial-management product is stable.

Possible scope:

- investment accounts and positions;
- asset registration;
- purchase and sale operations;
- quantity and average purchase price;
- portfolio allocation;
- current market value;
- unrealized performance;
- dividends and investment income;
- historical portfolio evolution;
- market-price synchronization;
- provider-independent `MarketDataPort`;
- one or more infrastructure adapters for market-data providers;
- caching and rate-limit handling where necessary.

The first provider should favor a free or open-source-compatible source when technically and legally appropriate. Paid providers can be added later without changing the investment domain.

Expected concept:

```text
Android Investments UI
        ↓
Investments Use Cases
        ↓
MarketDataPort
        ↓
Market Data Adapter
        ↓
External Provider
```

---

## Phase 17 — Open Finance Integration

Introduce Open Finance only after authentication, the core financial domain, and synchronization concepts are stable.

This phase is primarily intended as an advanced integration and portfolio exercise. Initial development should use sandbox or simulated providers while following official Open Finance concepts and specifications as closely as practical.

Possible scope:

- financial institution discovery;
- consent creation and lifecycle management;
- authorization flow integration;
- OAuth 2.0 / OpenID Connect / FAPI study and implementation;
- bank accounts and balances;
- bank transaction synchronization;
- credit-card data where supported;
- imported-transaction reconciliation;
- automatic category suggestions for imported transactions;
- idempotent synchronization;
- consent expiration and revocation;
- provider-independent `OpenFinancePort`;
- sandbox/mock adapter;
- production-compatible adapter only when participation and security requirements are appropriate;
- retry, observability, and external-error handling;
- security and integration tests.

Expected concept:

```text
Android Bank Connections UI
        ↓
Open Finance Use Cases
        ↓
OpenFinancePort
        ↓
Open Finance Adapter
        ↓
Sandbox / Compatible Provider
```

Protected production banking-data access must not be assumed to be a public API capability. Any future production integration must respect the certification, participation, consent, certificate, and security requirements applicable to the Open Finance ecosystem.

---

# 🧭 Development Principles

MilleStone follows a few important principles:

### Build the smallest useful feature

Avoid implementing speculative functionality before it is necessary.

### Prefer clarity over cleverness

Code should be easy to understand and maintain.

### Keep the domain independent

Important business rules should not be tightly coupled to frameworks.

### Mobile first

The Android experience is the primary product interface.

### Backend as the source of truth

Server-side persistence and core business state remain centralized in the backend.

### Automate later

Manual financial management must work extremely well before advanced automation is introduced.

### Privacy by design

Financial data and Android permissions must be treated carefully from the beginning.

### Evolve incrementally

The architecture should support growth without requiring premature complexity.

---

# 📚 Documentation

Development documentation lives under:

```text
docs/
```

Architecture decisions that deserve a permanent record should live under:

```text
decisions/
```

The documentation roadmap will evolve together with the new Android direction.

---

# 📄 License

License has not yet been defined.

---

# 👤 Author

**Paulo Gandolfi**

MilleStone is a personal project focused on building a practical, maintainable, and useful personal finance application while continuously evolving its architecture and product capabilities.
