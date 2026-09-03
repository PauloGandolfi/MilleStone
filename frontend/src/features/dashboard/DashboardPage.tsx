export function DashboardPage() {
  return (
    <section className="page-section">
      <span className="eyebrow">Overview</span>
      <h1>Dashboard</h1>
      <p>
        Your financial overview will live here once the first vertical slices are
        connected to the backend.
      </p>

      <div className="placeholder-grid" aria-label="Dashboard placeholders">
        <article className="placeholder-card">
          <span>Balance</span>
          <strong>Coming soon</strong>
        </article>
        <article className="placeholder-card">
          <span>Income</span>
          <strong>Coming soon</strong>
        </article>
        <article className="placeholder-card">
          <span>Expenses</span>
          <strong>Coming soon</strong>
        </article>
      </div>
    </section>
  );
}
