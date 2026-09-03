# Phase 3 — Backend Architecture & Domain Foundation

## Objective

Establish MilleStone's minimal backend architecture and shared domain foundations without introducing feature-specific abstractions before they are needed.

## Structure

```text
io.github.paulogandolfi.milestone/
├── common/
│   ├── domain/
│   │   └── Money.java
│   ├── error/
│   │   ├── ConflictException.java
│   │   ├── DomainException.java
│   │   ├── ErrorCodes.java
│   │   └── ResourceNotFoundException.java
│   └── rest/
│       ├── ApiErrorResponse.java
│       ├── FieldErrorResponse.java
│       └── GlobalExceptionHandler.java
└── modules/
    └── package-info.java
```

Feature module packages are intentionally not created yet. They will be introduced with the vertical slices that need them.

## Money

`Money` is a framework-free value object based on `BigDecimal`.

Rules:

- scale is always `2`;
- rounding uses `RoundingMode.HALF_EVEN`;
- default currency is BRL;
- negative amounts are rejected;
- addition, subtraction, and comparison require matching currencies;
- subtraction cannot produce a negative `Money` value;
- `BigDecimal` inherently excludes NaN and infinite numeric values.

## Error Handling

The REST layer now exposes a standard error payload containing:

- timestamp;
- HTTP status;
- HTTP error name;
- stable application error code;
- sanitized message;
- field validation errors when applicable.

`GlobalExceptionHandler` translates:

- Bean Validation errors to HTTP 400;
- missing resources to HTTP 404;
- conflicts to HTTP 409;
- domain rule violations to HTTP 400;
- unexpected runtime errors to HTTP 500 with a sanitized client-facing message.

## Dependency Adjustment

`spring-boot-starter-validation` was added because validation is now an explicit cross-cutting REST concern and will be used by upcoming request DTOs.

## Tests

- `MoneyTest` covers scale, rounding, BRL default, arithmetic, comparison, null/negative invariants, and currency compatibility.
- `GlobalExceptionHandlerTest` covers validation, domain, missing-resource, conflict, and unexpected-error translations.

## Architectural Principle

Prefer clarity over ceremony. Ports, use cases, persistence adapters, and feature packages must be introduced only when a concrete vertical slice requires those boundaries.
