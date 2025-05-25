import React from 'react';
import './TransactionsTable.css';

interface Transaction {
  date: string;
  category: string;
  description: string;
  amount: number;
  type: 'income' | 'expense';
}

interface TransactionsTableProps {
  transactions: Transaction[];
  formatCurrency: (amount: number) => string;
  onNewTransaction?: () => void;
  onNewBudget?: () => void;
  onNewGoal?: () => void;
}

const TransactionsTable: React.FC<TransactionsTableProps> = ({ 
  transactions, 
  formatCurrency,
  onNewTransaction,
  onNewBudget,
  onNewGoal
}) => {
  return (
    <div className="transactions-container">
      <div className="transactions-header">
        <h3>Transacciones Recientes</h3>
        <div className="action-buttons">
          <button 
            className="btn btn-primary"
            onClick={onNewTransaction}
          >
            + Nueva Transacción
          </button>
          <button 
            className="btn btn-secondary"
            onClick={onNewBudget}
          >
            + Añadir Presupuesto
          </button>
          <button 
            className="btn btn-accent"
            onClick={onNewGoal}
          >
            + Nueva Meta
          </button>
        </div>
      </div>
      
      <div className="transactions-table">
        <div className="table-header">
          <div className="col-date">Fecha</div>
          <div className="col-category">Categoría</div>
          <div className="col-description">Descripción</div>
          <div className="col-amount">Monto</div>
          <div className="col-type">Tipo</div>
        </div>
        
        {transactions.map((transaction, index) => (
          <div key={index} className="table-row">
            <div className="col-date">{transaction.date}</div>
            <div className="col-category">{transaction.category}</div>
            <div className="col-description">{transaction.description}</div>
            <div className={`col-amount ${transaction.type}`}>
              {formatCurrency(Math.abs(transaction.amount))}
            </div>
            <div className={`col-type ${transaction.type}`}>
              <span className={`type-badge ${transaction.type}`}>
                {transaction.type === 'income' ? 'Ingreso' : 'Gasto'}
              </span>
            </div>
          </div>
        ))}
        
        {transactions.length === 0 && (
          <div className="empty-state">
            <div className="empty-icon">📊</div>
            <p>No hay transacciones recientes</p>
            <button 
              className="btn btn-primary"
              onClick={onNewTransaction}
            >
              Agregar primera transacción
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default TransactionsTable;