import React from 'react';
import './MetricCard.css';

interface MetricCardProps {
  title: string;
  amount: number;
  icon: string;
  type: 'balance' | 'income' | 'expenses' | 'savings';
  formatCurrency: (amount: number) => string;
}

const MetricCard: React.FC<MetricCardProps> = ({ title, amount, icon, type, formatCurrency }) => {
  return (
    <div className={`summary-card ${type}`}>
      <div className="card-icon">{icon}</div>
      <div className="card-content">
        <h3>{title}</h3>
        <p className="amount">{formatCurrency(amount)}</p>
      </div>
    </div>
  );
};

export default MetricCard;