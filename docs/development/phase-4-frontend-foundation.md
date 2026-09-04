# Phase 4 — Frontend Foundation

> **Status:** ✅ Complete
>
> **Next:** Phase 5 — Vertical Slice 0: Frontend ↔ Backend ↔ Database

## Objective

Establish the MilleStone web frontend foundation with React, TypeScript, Vite, client-side routing, shared HTTP/error handling, and a responsive application shell without introducing business-feature logic prematurely.

## Implemented foundation

- React 19 with TypeScript and Vite.
- Strict TypeScript project configuration and `@/*` source alias.
- React Router routes for Dashboard, Transactions, Categories, Budgets, and Goals.
- Responsive green-and-white MilleStone application shell.
- Shared feedback components for loading, error, and empty states.
- Central `apiClient` with configurable API base URL and standard backend error parsing.
- Frontend `ApiError` and `FieldError` contracts aligned with the Phase 3 backend error payload.
- `.env.example` for local API configuration while real environment files remain ignored by Git.

## Architectural rules

- Feature code lives under `src/features/<feature>`.
- Shared infrastructure belongs in `src/services`, `src/types`, and `src/components`.
- Do not introduce global state libraries until a concrete feature requires them.
- Do not implement category, transaction, budget, goal, or dashboard business logic in this phase.
- API access should flow through the shared client rather than ad-hoc `fetch` calls in feature components.

## Local development

```bash
cd frontend
cp .env.example .env.development
npm install
npm run dev
```

The application runs at `http://localhost:5173` and expects the backend API at `http://localhost:8080/api/v1` by default.

## Validation

```bash
npm run typecheck
npm run build
```

## Done when

Phase 4 is complete when the frontend can boot, navigate between base routes without full-page reloads, build under strict TypeScript settings, and has a reusable HTTP/error-handling foundation ready for the first frontend-to-backend vertical slice.
