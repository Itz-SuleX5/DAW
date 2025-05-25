import React, { useState, useEffect } from 'react';
import './Dashboard.css';
import MetricCard from './components/MetricCard';
import MonthlyChart from './components/MonthlyChart';
import ExpenseChart from './components/ExpenseChart';
import TransactionsTable from './components/TransactionsTable';

interface DashboardData {
  totalBalance: number;
  income: number;
  expenses: number;
  savings: number;
  monthlyData: MonthlyData[];
  expenseCategories: ExpenseCategory[];
  recentTransactions: Transaction[];
}

interface MonthlyData {
  month: string;
  income: number;
  expenses: number;
}

interface ExpenseCategory {
  name: string;
  amount: number;
  color: string;
}

interface Transaction {
  date: string;
  category: string;
  description: string;
  amount: number;
  type: 'income' | 'expense';
}

const Dashboard: React.FC = () => {
  const [dashboardData, setDashboardData] = useState<DashboardData>({
    totalBalance: 4580.75,
    income: 3200.00,
    expenses: 2600.00,
    savings: 1980.75,
    monthlyData: [
      { month: 'Ene', income: 2400, expenses: 1800 },
      { month: 'Feb', income: 2600, expenses: 1900 },
      { month: 'Mar', income: 2400, expenses: 2300 },
      { month: 'Abr', income: 2800, expenses: 2200 },
      { month: 'May', income: 2600, expenses: 2400 },
      { month: 'Jun', income: 3000, expenses: 2500 }
    ],
    expenseCategories: [
      { name: 'Alimentación', amount: 650, color: '#FF6B35' },
      { name: 'Transporte', amount: 450, color: '#4ECDC4' },
      { name: 'Servicios', amount: 380, color: '#45B7D1' },
      { name: 'Entretenimiento', amount: 320, color: '#96CEB4' },
      { name: 'Otros', amount: 800, color: '#FFEAA7' }
    ],
    recentTransactions: [
      { date: '15 Feb 2024', category: 'Alimentación', description: 'Supermercado Local', amount: -20.50, type: 'expense' },
      { date: '14 Feb 2024', category: 'Salario', description: 'Pago Mensual', amount: 3000.00, type: 'income' },
      { date: '14 Feb 2024', category: 'Transporte', description: 'Gasolina', amount: -45.00, type: 'expense' },
      { date: '13 Feb 2024', category: 'Servicios', description: 'Internet', amount: -60.00, type: 'expense' },
      { date: '13 Feb 2024', category: 'Freelance', description: 'Proyecto Diseño', amount: 500.00, type: 'income' }
    ]
  });

  const [selectedPeriod, setSelectedPeriod] = useState('6 meses');

  // Simulate data fetching
  useEffect(() => {
    // Here you would typically fetch data from your Spring Boot backend
    // fetchDashboardData().then(setDashboardData);
  }, []);

  const formatCurrency = (amount: number): string => {
    return new Intl.NumberFormat('es-ES', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 2
    }).format(amount);
  };



  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <div className="header-left">
          <div className="logo">
            <span className="logo-icon">💳</span>
            <span className="logo-text">Whiskers Wallet</span>
          </div>
          <nav className="nav-menu">
            <a href="#" className="nav-item active">Dashboard</a>
            <a href="#" className="nav-item">Transacciones</a>
            <a href="#" className="nav-item">Presupuestos</a>
            <a href="#" className="nav-item">Metas</a>
            <a href="#" className="nav-item">Cuentas</a>
          </nav>
        </div>
        <div className="header-right">
          <span className="user-name">Alexis</span>
          <button className="logout-btn">🚪 Cerrar Sesión</button>
        </div>
      </header>

      <main className="dashboard-content">
        {/* Summary Cards */}
        <div className="summary-cards">
          <MetricCard
            title="Balance Total"
            amount={dashboardData.totalBalance}
            icon="💰"
            type="balance"
            formatCurrency={formatCurrency}
          />
          <MetricCard
            title="Ingresos"
            amount={dashboardData.income}
            icon="📈"
            type="income"
            formatCurrency={formatCurrency}
          />
          <MetricCard
            title="Gastos"
            amount={dashboardData.expenses}
            icon="📉"
            type="expenses"
            formatCurrency={formatCurrency}
          />
          <MetricCard
            title="Ahorros"
            amount={dashboardData.savings}
            icon="🏦"
            type="savings"
            formatCurrency={formatCurrency}
          />
        </div>

        <div className="dashboard-grid">
          <ExpenseChart categories={dashboardData.expenseCategories} />
          <MonthlyChart data={dashboardData.monthlyData} formatCurrency={formatCurrency} />
        </div>

        {/* Recent Transactions */}
        <TransactionsTable
          transactions={dashboardData.recentTransactions}
          formatCurrency={formatCurrency}
          onNewTransaction={() => console.log('Nueva transacción')}
          onNewBudget={() => console.log('Nuevo presupuesto')}
          onNewGoal={() => console.log('Nueva meta')}
        />
      </main>
    </div>
  );
};

export default Dashboard;