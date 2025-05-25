import React from 'react';
import './MonthlyChart.css';

interface MonthlyData {
  month: string;
  income: number;
  expenses: number;
}

interface MonthlyChartProps {
  data: MonthlyData[];
  formatCurrency: (amount: number) => string;
}

const MonthlyChart: React.FC<MonthlyChartProps> = ({ data, formatCurrency }) => {
  const getMaxValue = () => {
    return Math.max(...data.flatMap(d => [d.income, d.expenses]));
  };

  const calculatePercentage = (value: number, max: number): number => {
    return (value / max) * 100;
  };

  return (
    <div className="chart-container monthly-chart">
      <h3>Ingresos y Gastos Mensuales</h3>
      <div className="bar-chart">
        <div className="chart-bars">
          {data.map((monthData, index) => {
            const maxValue = getMaxValue();
            const incomeHeight = calculatePercentage(monthData.income, maxValue);
            const expenseHeight = calculatePercentage(monthData.expenses, maxValue);
            
            return (
              <div key={monthData.month} className="bar-group">
                <div className="bars">
                  <div 
                    className="bar income-bar" 
                    style={{ height: `${incomeHeight}%` }}
                    title={`Ingresos: ${formatCurrency(monthData.income)}`}
                  ></div>
                  <div 
                    className="bar expense-bar" 
                    style={{ height: `${expenseHeight}%` }}
                    title={`Gastos: ${formatCurrency(monthData.expenses)}`}
                  ></div>
                </div>
                <span className="bar-label">{monthData.month}</span>
              </div>
            );
          })}
        </div>
        <div className="chart-legend">
          <div className="legend-item">
            <div className="legend-color income-color"></div>
            <span>Ingresos</span>
          </div>
          <div className="legend-item">
            <div className="legend-color expense-color"></div>
            <span>Gastos</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MonthlyChart;