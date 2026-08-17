# MilleStone

Android application for **personal finance management**, built with a focus on simplicity, privacy, and incremental evolution.

The goal of MilleStone is to help users understand and manage their financial life by tracking income, expenses, recurring costs, budgets, and financial goals with as little manual effort as possible.

In the future, the application may automatically identify financial transactions through banking app notifications, as long as the user explicitly enables this functionality.

---

## 📌 Project Status

> 🚧 **Under development**

The project is currently in its initial product definition and technical foundation phase.

### Current Version

`0.1.0-SNAPSHOT`

### Current Milestone

**M0 — Android Foundation**

Main objective:

```text
Add transaction
        ↓
Persist locally
        ↓
Update dashboard
```

---

## 🎯 Purpose

MilleStone was created with a simple idea:

> Make personal finance easier to understand without turning financial tracking into another daily burden.

The application should quickly help users answer questions such as:

* How much did I earn this month?
* How much have I spent?
* How much can I still spend?
* What are my biggest expenses?
* How much do I still have in upcoming expenses?
* Am I staying within my budget?
* How are my financial goals progressing?

---

# 🚀 MVP

The first version of MilleStone will follow a **local-first** approach.

Financial data will remain stored on the device and no account or backend connection will be required.

## Planned Features

### Income

Register financial income such as:

* salary;
* payments;
* received transfers;
* freelance income;
* other income.

### Expenses

Register expenses such as:

* food;
* transportation;
* housing;
* entertainment;
* healthcare;
* shopping;
* subscriptions;
* other expenses.

### Categories

Transactions can be organized into configurable categories.

Examples:

```text
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

---

### Recurring Expenses

Users will be able to register recurring expenses such as:

```text
Rent
Internet
Electricity
Gym
Streaming services
Mobile plan
Loans
```

Recurring rules may automatically generate expected transactions for each period.

---

### Dashboard

The dashboard will provide a quick overview of the user's financial situation.

Planned indicators:

* current balance;
* projected balance;
* monthly income;
* monthly expenses;
* budget usage;
* expenses by category;
* upcoming expenses;
* financial goal progress.

---

### Monthly Budget

Users will be able to define a monthly spending limit.

Example:

```text
Monthly Budget

R$ 3,000.00

Used
R$ 1,840.00

Remaining
R$ 1,160.00
```

Category-specific budgets may also be supported.

---

### Financial Goals

Users will be able to track financial objectives.

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

---

# 🤖 Financial Automation

One of the most important future features of MilleStone will be the automatic detection of financial transactions through banking application notifications.

This functionality will be **optional**.

The user must explicitly grant notification access permission.

Example:

```text
Nubank

Purchase approved
R$ 37.90 at iFood
```

Expected flow:

```text
Banking App
        ↓
Android Notification
        ↓
NotificationListenerService
        ↓
BankNotificationParser
        ↓
ParsedTransaction
        ↓
Validation
        ↓
Transaction
```

---

## Bank Notification Parsers

Each financial institution may have its own parser.

Example:

```text
BankNotificationParser
        │
        ├── NubankNotificationParser
        ├── ItauNotificationParser
        ├── InterNotificationParser
        └── GenericNotificationParser
```

This allows support for additional banks without coupling institution-specific rules to the core financial domain.

---

# 🔐 Privacy

Privacy is one of the core principles of MilleStone.

The application will initially follow a:

> **Local-first approach.**

In the first version:

* no financial data will be sent to external servers;
* no user account will be required;
* no login will be required;
* transactions will be stored locally;
* notification access will be optional;
* banking notifications will be processed locally.

If cloud synchronization is introduced in the future, it should remain an independent and explicitly enabled feature.

---

# 🛠️ Tech Stack

## Android

```text
Kotlin
Jetpack Compose
Material 3
ViewModel
StateFlow
Coroutines
Navigation Compose
```

## Persistence

```text
Room
SQLite
DataStore
```

## Testing

```text
JUnit
Kotlin Test
AndroidX Test
```

Additional tools may be introduced as the project evolves.

---

# 🏗️ Architecture

MilleStone will follow an architecture inspired by **Clean Architecture / Ports and Adapters**, keeping the business domain independent from the Android framework.

Simplified view:

```text
┌─────────────────────────────────────────┐
│             PRESENTATION                │
│                                         │
│   Jetpack Compose                       │
│   Screens                               │
│   ViewModels                            │
│   UI State                              │
└───────────────────┬─────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────┐
│             APPLICATION                 │
│                                         │
│   Use Cases                             │
│   Application Services                  │
└───────────────────┬─────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────┐
│                DOMAIN                   │
│                                         │
│   Entities                              │
│   Value Objects                         │
│   Repository Ports                      │
│   Business Rules                        │
└───────────────────▲─────────────────────┘
                    │
                    │ implements
                    │
┌───────────────────┴─────────────────────┐
│                 DATA                    │
│                                         │
│   Room                                  │
│   Repository Implementations            │
│   Data Sources                          │
│   Notification Parsers                  │
└─────────────────────────────────────────┘
```

An important rule:

```text
DOMAIN
```

must not depend on:

```text
Android
Compose
Room
NotificationListenerService
```

---

# 📦 Planned Project Structure

The structure may evolve during development.

```text
com.milestone
│
├── domain
│   ├── model
│   ├── repository
│   └── service
│
├── application
│   └── usecase
│
├── data
│   ├── local
│   │   ├── database
│   │   ├── dao
│   │   └── entity
│   │
│   ├── repository
│   │
│   └── notification
│       ├── listener
│       └── parser
│
├── presentation
│   ├── dashboard
│   ├── transaction
│   ├── category
│   ├── budget
│   ├── goal
│   └── settings
│
└── di
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
AppSettings
```

---

## Transaction

Represents a financial transaction.

Types:

```text
INCOME
EXPENSE
```

Sources:

```text
MANUAL
NOTIFICATION
IMPORT
SYNC
```

Even though the first version will only use `MANUAL`, the domain should already support additional transaction sources in the future.

---

# 🗺️ Roadmap

## M0 — Android Foundation

* [ ] Create Android project
* [ ] Configure Kotlin
* [ ] Configure Jetpack Compose
* [ ] Define architectural structure
* [ ] Configure navigation
* [ ] Configure Room
* [ ] Configure DataStore
* [ ] Create initial theme
* [ ] Configure testing structure

---

## M1 — Transactions

* [ ] Create `Transaction` domain model
* [ ] Create categories
* [ ] Create transaction
* [ ] Edit transaction
* [ ] Delete transaction
* [ ] List transactions
* [ ] Persist transactions locally

Expected first complete flow:

```text
New Transaction
        ↓
Use Case
        ↓
Repository
        ↓
Room
        ↓
Dashboard
```

---

## M2 — Dashboard

* [ ] Monthly balance
* [ ] Income
* [ ] Expenses
* [ ] Expenses by category
* [ ] Recent transaction history
* [ ] Projected balance

---

## M3 — Recurring Transactions

* [ ] Create recurring expenses
* [ ] Create recurring income
* [ ] Generate expected transactions
* [ ] Manage active recurring rules

---

## M4 — Budget

* [ ] Monthly budget
* [ ] Category budget
* [ ] Budget usage percentage
* [ ] Budget alerts

---

## M5 — Financial Goals

* [ ] Create financial goal
* [ ] Update progress
* [ ] Complete goal
* [ ] Financial goal history

---

## M6 — Bank Notifications

* [ ] Create `NotificationListenerService`
* [ ] Request notification access
* [ ] Create `BankNotificationParser`
* [ ] Implement first bank parser
* [ ] Detect financial transactions
* [ ] Create transaction confirmation flow
* [ ] Detect possible duplicates

---

## M7 — Automation

* [ ] Automatic categorization
* [ ] Merchant-based rules
* [ ] Support additional financial institutions
* [ ] Confidence levels
* [ ] Learn from user decisions

---

## M8 — Synchronization

Evaluate only after there is a real need for cloud synchronization.

Possible architecture:

```text
Android
    ↓
REST API
    ↓
Spring Boot
    ↓
PostgreSQL
```

---

## M9 — Distribution

* [ ] Test on multiple devices
* [ ] Create privacy policy
* [ ] Review permissions
* [ ] Closed beta
* [ ] Configure Google Play Console
* [ ] Publish to Google Play

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
feature/transaction-domain
feature/transaction-create
feature/dashboard
feature/monthly-budget
feature/notification-listener

fix/transaction-validation

refactor/transaction-repository

docs/update-readme
```

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

feat: add transaction persistence with Room

fix: prevent negative expense amount

test: add transaction use case tests

refactor: extract transaction repository

docs: update project roadmap
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
│   └── decisions/
│
├── features/
│
└── development/
```

Relevant architectural decisions can be documented using ADRs:

```text
docs/architecture/decisions/

ADR-001-local-first.md
ADR-002-clean-architecture.md
ADR-003-room-database.md
ADR-004-bank-notification-parser.md
```

---

# ⚙️ Development Environment

Initial requirements:

```text
Android Studio
Android SDK
JDK 17+
Git
```

Specific Android SDK, Gradle, Kotlin, and dependency versions should always follow the actual project configuration.

---

# 📋 Project Principles

The following principles should guide the development of MilleStone.

### Simplicity Before Abstraction

Do not build infrastructure for problems that do not exist yet.

### Local First

Keep financial data on the device until there is a real need for a backend.

### Independent Domain

Financial business rules should not directly depend on Android-specific technologies.

### Reliable Automation

Automatically detected transactions should never compromise the integrity of financial data.

### Incremental Evolution

Every milestone should result in a functional and testable version of the application.

### Testability

Relevant business rules should be testable without depending on the Android UI.

---

# 📄 License

Private project.

All rights reserved.

---

# MilleStone

> **Understand your money. Reach your milestones.**
