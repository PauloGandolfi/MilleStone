# MilleStone

Web application for **personal finance management**, built with a focus on simplicity, privacy, maintainability, and incremental evolution.

The goal of MilleStone is to help users understand and manage their financial life by tracking income, expenses, recurring costs, budgets, and financial goals through a simple and intuitive interface.

MilleStone starts as a web application, with a backend built in **Java** and a frontend built with **React and TypeScript**.

---

## 📌 Project Status

> 🚧 **Under development**

The project is currently in its initial product definition and technical foundation phase.

### Current Version

`0.1.0-SNAPSHOT`

### Current Milestone

**M0 — Web Foundation**

Main objective:

```text
Create transaction
        ↓
REST API
        ↓
Persist transaction
        ↓
Update dashboard
```

The first milestone should establish a complete vertical slice between frontend, backend, and database.

---

# 🎯 Purpose

MilleStone was created with a simple idea:

> Make personal finance easier to understand without turning financial tracking into another daily burden.

The application should quickly help users answer questions such as:

* How much did I earn this month?
* How much have I spent?
* How much money do I currently have available?
* What are my biggest expenses?
* How much do I still have in upcoming expenses?
* Am I staying within my budget?
* How are my financial goals progressing?

The product should prioritize clarity and usefulness over unnecessary complexity.

---

# 🚀 MVP

The first version of MilleStone will focus on the essential personal finance workflow.

```text
Income
   +
Expenses
   ↓
Financial Overview
   ↓
Budget
   ↓
Goals
```

The initial objective is not to create a complete banking platform.

The objective is to create a reliable personal finance application that can evolve incrementally.

---

## 💰 Income

Users will be able to register financial income such as:

* salary;
* payments;
* received transfers;
* freelance income;
* bonuses;
* other income.

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

## 💸 Expenses

Users will be able to register expenses such as:

* food;
* transportation;
* housing;
* entertainment;
* healthcare;
* shopping;
* subscriptions;
* education;
* other expenses.

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

Categories should eventually be customizable by the user.

---

# 🔁 Recurring Transactions

Users will be able to register recurring income and expenses.

Examples:

```text
Salary

Rent
Internet
Electricity
Gym
Streaming services
Mobile plan
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

The dashboard will provide a quick overview of the user's financial situation.

Planned indicators:

* current balance;
* projected balance;
* monthly income;
* monthly expenses;
* budget usage;
* expenses by category;
* upcoming expenses;
* recent transactions;
* financial goal progress.

The dashboard should answer the most important financial questions without requiring the user to navigate through multiple screens.

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

Example:

```text
Food

Budget:
R$ 800.00

Used:
R$ 520.00

Remaining:
R$ 280.00
```

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

Possible goals:

```text
Emergency Fund

Travel

New Computer

Car

House

Debt Payment
```

---

# 🤖 Financial Automation

Automation remains an important future objective for MilleStone.

However, the initial web application will not depend on Android notification access.

Possible future automation strategies include:

```text
Manual Transaction
        ↓
MilleStone
```

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

```text
Future Mobile Application
        ↓
Bank Notification
        ↓
Transaction Parser
        ↓
MilleStone API
```

Automation should be implemented only when the core financial workflow is stable.

---

# 🔐 Privacy and Security

Financial information is sensitive and should be handled carefully.

MilleStone should follow these principles:

* financial data should never be exposed unnecessarily;
* database access must remain exclusively behind the backend;
* credentials and secrets must never be stored in the frontend;
* the frontend must communicate with the backend through defined APIs;
* sensitive configuration must use environment variables;
* logs must avoid exposing sensitive financial information;
* external integrations should be explicitly enabled;
* authentication must be implemented before exposing personal financial data publicly.

During the initial development phase, the application may run as a local development environment without external access.

---

# 🛠️ Tech Stack

## Backend

```text
Java
Spring Boot
Spring Web
Spring Data JPA
Bean Validation
PostgreSQL
Maven
```

Additional Spring modules should only be introduced when required.

Possible future additions:

```text
Spring Security
Flyway
Testcontainers
OpenAPI
Docker
```

---

## Frontend

```text
React
TypeScript
Vite
React Router
```

Additional libraries should be introduced according to actual product needs.

Possible additions:

```text
TanStack Query
React Hook Form
Zod
Axios
```

The project should avoid unnecessary dependencies during the initial milestones.

---

## Database

```text
PostgreSQL
```

Database schema evolution should eventually be managed through migrations.

Recommended option:

```text
Flyway
```

---

## Testing

### Backend

```text
JUnit
Mockito
Spring Boot Test
Testcontainers
```

### Frontend

```text
Vitest
React Testing Library
```

End-to-end testing may be introduced after the primary flows are stable.

---

# 🏗️ Architecture

MilleStone should keep business rules independent from frameworks whenever practical.

The backend will follow an architecture inspired by:

> **Clean Architecture / Hexagonal Architecture / Ports and Adapters**

Simplified view:

```text
┌──────────────────────────────────────────┐
│                 FRONTEND                 │
│                                          │
│        React + TypeScript                │
│                                          │
│  Pages                                   │
│  Components                              │
│  Hooks                                   │
│  API Clients                             │
└────────────────────┬─────────────────────┘
                     │
                     │ HTTP / REST
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
│          INFRASTRUCTURE / ADAPTERS        │
│                                          │
│  PostgreSQL                              │
│  JPA                                     │
│  External APIs                           │
│  Import / Export                         │
└──────────────────────────────────────────┘
```

An important rule:

```text
DOMAIN
```

should not depend directly on:

```text
Spring
JPA
PostgreSQL
HTTP
React
```

Framework-specific concerns should remain outside the core business domain whenever reasonable.

---

# 🌐 Application Flow

A typical operation should follow a flow similar to:

```text
React
  ↓
HTTP Request
  ↓
REST Controller
  ↓
Application Use Case
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
Application
  ↓
REST Controller
  ↓
JSON
  ↓
React
```

---

# 📦 Planned Repository Structure

MilleStone will initially use a single repository containing backend, frontend, and documentation.

```text
milestone/
│
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── README.md
│
├── frontend/
│   ├── src/
│   ├── package.json
│   └── README.md
│
├── docs/
│
├── .gitignore
├── README.md
└── docker-compose.yml
```

The structure may evolve as the project grows.

---

# ☕ Backend Structure

Base package:

```text
io.github.paulogandolfi.milestone
```

Initial suggested structure:

```text
io.github.paulogandolfi.milestone
│
├── domain
│   ├── model
│   ├── service
│   └── port
│
├── application
│   ├── usecase
│   └── service
│
├── adapter
│   ├── in
│   │   └── web
│   │
│   └── out
│       └── persistence
│
└── config
```

As the application grows, organization by business feature may be preferred over a large technical-layer structure.

Example:

```text
milestone
│
├── transaction
├── category
├── budget
├── goal
└── shared
```

Architecture should serve the project instead of becoming a source of unnecessary complexity.

---

# ⚛️ Frontend Structure

Initial suggestion:

```text
src/
│
├── app/
│   ├── routes/
│   └── providers/
│
├── features/
│   ├── dashboard/
│   ├── transactions/
│   ├── categories/
│   ├── budgets/
│   └── goals/
│
├── components/
│
├── services/
│   └── api/
│
├── hooks/
│
├── types/
│
└── main.tsx
```

Business-specific components should preferably remain inside their respective feature.

Reusable UI components may live in:

```text
components/
```

---

# 🧠 Initial Domain Model

Main planned entities:

```text
Transaction
Category
RecurringRule
MonthlyBudget
FinancialGoal
User
```

Not every planned entity needs to be implemented immediately.

The model should evolve according to actual requirements.

---

# 💵 Transaction

Represents a financial transaction.

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

The first version will primarily use:

```text
MANUAL
```

Example representation:

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

Exact domain and persistence models should be defined during implementation.

---

# 🗺️ Roadmap

## M0 — Web Foundation

* [ ] Create repository structure
* [ ] Create Spring Boot backend
* [ ] Create React + TypeScript frontend
* [ ] Configure PostgreSQL
* [ ] Configure database connection
* [ ] Define backend architecture
* [ ] Define frontend structure
* [ ] Configure frontend routing
* [ ] Configure backend testing
* [ ] Configure frontend testing
* [ ] Configure development environment
* [ ] Create initial application layout
* [ ] Connect frontend to backend

Expected result:

```text
React
   ↓
Spring Boot
   ↓
PostgreSQL
```

---

## M1 — Transactions

* [ ] Create `Transaction` domain model
* [ ] Create transaction API
* [ ] Create transaction
* [ ] Edit transaction
* [ ] Delete transaction
* [ ] List transactions
* [ ] Persist transactions
* [ ] Create transaction frontend
* [ ] Integrate frontend with API
* [ ] Add transaction validation

Expected first complete business flow:

```text
New Transaction
        ↓
React Form
        ↓
REST API
        ↓
Use Case
        ↓
Repository
        ↓
PostgreSQL
        ↓
Dashboard
```

---

## M2 — Dashboard

* [ ] Monthly balance
* [ ] Monthly income
* [ ] Monthly expenses
* [ ] Expenses by category
* [ ] Recent transaction history
* [ ] Projected balance
* [ ] Dashboard API
* [ ] Dashboard interface

---

## M3 — Recurring Transactions

* [ ] Create recurring expense
* [ ] Create recurring income
* [ ] Define recurrence rules
* [ ] Generate expected transactions
* [ ] Manage active recurring rules
* [ ] Display upcoming transactions

---

## M4 — Budget

* [ ] Monthly budget
* [ ] Category budget
* [ ] Budget usage percentage
* [ ] Remaining budget
* [ ] Budget alerts
* [ ] Budget dashboard integration

---

## M5 — Financial Goals

* [ ] Create financial goal
* [ ] Update goal progress
* [ ] Complete goal
* [ ] Goal history
* [ ] Goal dashboard integration

---

## M6 — Authentication

Authentication becomes mandatory before the application is exposed publicly.

Possible scope:

* [ ] Create user model
* [ ] User registration
* [ ] Login
* [ ] Password hashing
* [ ] Authentication
* [ ] Authorization
* [ ] Protect financial data by user
* [ ] Session or token strategy
* [ ] Security tests

Technology decisions should be made when this milestone begins instead of prematurely.

---

## M7 — Financial Automation

Evaluate strategies such as:

* [ ] Transaction import
* [ ] Bank statement parsing
* [ ] CSV import
* [ ] OFX import
* [ ] Automatic categorization
* [ ] Merchant-based rules
* [ ] Duplicate detection
* [ ] Confidence levels
* [ ] Learn from user categorization decisions

---

## M8 — Financial Integrations

Only evaluate external banking integrations after the core product is stable.

Possible architecture:

```text
Financial Provider
        ↓
Integration Adapter
        ↓
Application Port
        ↓
Transaction Processing
        ↓
MilleStone
```

Potential integrations must be evaluated considering:

* security;
* privacy;
* API availability;
* cost;
* reliability;
* financial regulations.

---

## M9 — Mobile

A mobile application may be evaluated after the web platform and API are stable.

Possible architecture:

```text
                    ┌── React Web
                    │
                    ▼
              MilleStone API
                    ▲
                    │
                    └── Mobile App
```

The backend should remain reusable regardless of the client application.

---

## M10 — Distribution

* [ ] Production environment
* [ ] HTTPS
* [ ] Production database
* [ ] Backup strategy
* [ ] Security review
* [ ] Privacy policy
* [ ] Monitoring
* [ ] Error tracking
* [ ] Deployment pipeline
* [ ] Public beta

---

# 🌿 Branch Strategy

Initial suggestion:

```text
main
develop

feature/*
fix/*
refactor/*
docs/*
```

Examples:

```text
feature/backend-foundation

feature/frontend-foundation

feature/transaction-domain

feature/transaction-api

feature/transaction-form

feature/dashboard

feature/monthly-budget

fix/transaction-validation

refactor/transaction-repository

docs/update-readme
```

Branches should remain short-lived whenever possible.

---

# 📝 Commit Convention

Prefer clear and objective commit messages using:

```text
feat:
fix:
refactor:
test:
docs:
chore:
```

Examples:

```text
feat: create transaction domain model

feat: add transaction rest endpoint

feat: create transaction form

feat: persist transactions with postgresql

fix: prevent invalid transaction amount

test: add transaction use case tests

refactor: extract transaction repository port

docs: update project roadmap

chore: configure development environment
```

---

# 📚 Documentation

Important documentation should be versioned together with the source code.

Suggested structure:

```text
docs/
│
├── product/
│   └── product-definition.md
│
├── architecture/
│   ├── overview.md
│   │
│   └── decisions/
│
├── features/
│
├── api/
│
└── development/
```

Relevant architectural decisions may be documented using ADRs.

Example:

```text
docs/architecture/decisions/

ADR-001-web-first.md

ADR-002-hexagonal-architecture.md

ADR-003-postgresql.md

ADR-004-rest-api.md

ADR-005-authentication-strategy.md
```

---

# ⚙️ Development Environment

Initial requirements:

```text
JDK
Maven

Node.js
npm

PostgreSQL

Git
```

Recommended development tools:

```text
IntelliJ IDEA
VS Code

Docker
Docker Compose
Postman / Bruno
```

Exact Java, Spring Boot, Node.js, React, TypeScript, and dependency versions should always follow the actual project configuration.

---

# 🖥️ Local Development

Expected development architecture:

```text
Browser
   ↓
React Development Server
   ↓
Spring Boot API
   ↓
PostgreSQL
```

Typical services:

```text
Frontend
localhost:5173

Backend
localhost:8080

PostgreSQL
localhost:5432
```

Exact ports may change according to the project configuration.

---

# 📋 Project Principles

The following principles should guide the development of MilleStone.

## Simplicity Before Abstraction

Do not build infrastructure for problems that do not exist yet.

Start with the simplest architecture capable of supporting the current requirements.

---

## Business Before Framework

Business rules belong to the application and domain, not to Spring, React, or PostgreSQL.

Frameworks are implementation details.

---

## Backend as the Source of Truth

Business rules and financial data integrity should be controlled by the backend.

The frontend should never become the authoritative source for important financial calculations.

---

## Explicit APIs

Communication between frontend and backend should happen through clear and predictable contracts.

---

## Privacy by Design

Financial data should be protected from the beginning.

Privacy should not be treated as a feature to be added later.

---

## Reliable Automation

Automatically imported or detected transactions should never compromise the integrity of financial data.

Automation must prefer confirmation over incorrect assumptions.

---

## Incremental Evolution

Every milestone should result in a functional and testable improvement to the product.

Avoid building future features before the current workflow is useful.

---

## Testability

Relevant business rules should be testable independently from:

```text
React
HTTP
Spring MVC
PostgreSQL
```

---

## Maintainability

Prefer:

```text
clear code
small responsibilities
explicit names
simple flows
useful tests
```

over unnecessary abstractions and premature generalization.

---

# 📄 License

Private project.

All rights reserved.

---

# MilleStone

> **Understand your money. Reach your milestones.**
