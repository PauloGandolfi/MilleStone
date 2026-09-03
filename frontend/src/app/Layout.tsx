import { NavLink, Outlet } from 'react-router-dom';

const navigation = [
  { to: '/dashboard', label: 'Dashboard' },
  { to: '/transactions', label: 'Transactions' },
  { to: '/categories', label: 'Categories' },
  { to: '/budgets', label: 'Budgets' },
  { to: '/goals', label: 'Goals' },
];

export function Layout() {
  return (
    <div className="app-shell">
      <aside className="sidebar" aria-label="Primary navigation">
        <div className="brand">
          <div className="brand-mark" aria-hidden="true">
            M
          </div>
          <div>
            <strong>MilleStone</strong>
            <span>Personal finance</span>
          </div>
        </div>

        <nav className="navigation">
          {navigation.map((item) => (
            <NavLink
              key={item.to}
              to={item.to}
              className={({ isActive }) =>
                isActive ? 'navigation-link active' : 'navigation-link'
              }
            >
              {item.label}
            </NavLink>
          ))}
        </nav>

        <div className="sidebar-footer">
          <span>Foundation</span>
          <strong>Phase 4</strong>
        </div>
      </aside>

      <div className="app-content">
        <header className="topbar">
          <div>
            <span className="topbar-label">MilleStone</span>
            <strong>Build better financial habits.</strong>
          </div>
          <div className="environment-badge">Local</div>
        </header>

        <main className="main-content">
          <Outlet />
        </main>
      </div>
    </div>
  );
}
