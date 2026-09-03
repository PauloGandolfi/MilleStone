import { Navigate, createBrowserRouter } from 'react-router-dom';

import { Layout } from '@/app/Layout';
import { BudgetsPage } from '@/features/budgets/BudgetsPage';
import { CategoriesPage } from '@/features/categories/CategoriesPage';
import { DashboardPage } from '@/features/dashboard/DashboardPage';
import { GoalsPage } from '@/features/goals/GoalsPage';
import { TransactionsPage } from '@/features/transactions/TransactionsPage';

function NotFoundPage() {
  return (
    <section className="page-section">
      <span className="eyebrow">404</span>
      <h1>Page not found</h1>
      <p>The page you requested does not exist in MilleStone.</p>
    </section>
  );
}

export const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      { index: true, element: <Navigate to="/dashboard" replace /> },
      { path: 'dashboard', element: <DashboardPage /> },
      { path: 'transactions', element: <TransactionsPage /> },
      { path: 'categories', element: <CategoriesPage /> },
      { path: 'budgets', element: <BudgetsPage /> },
      { path: 'goals', element: <GoalsPage /> },
      { path: '*', element: <NotFoundPage /> },
    ],
  },
]);
