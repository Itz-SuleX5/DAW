import React from 'react';
import './ExpenseChart.css';

interface ExpenseCategory {
  name: string;
  amount: number;
  color: string;
}

interface ExpenseChartProps {
  categories: ExpenseCategory[];
}

const ExpenseChart: React.FC<ExpenseChartProps> = ({ categories }) => {
  const total = categories.reduce((sum, cat) => sum + cat.amount, 0);

  return (
    <div className="chart-container expense-chart">
      <h3>Distribución de Gastos</h3>
      <div className="pie-chart">
        <svg viewBox="0 0 200 200" className="pie-svg">
          {categories.map((category, index) => {
            const percentage = (category.amount / total) * 100;
            const angle = (percentage / 100) * 360;
            const startAngle = categories
              .slice(0, index)
              .reduce((sum, cat) => sum + (cat.amount / total) * 360, 0);
            
            const x1 = 100 + 80 * Math.cos((startAngle - 90) * Math.PI / 180);
            const y1 = 100 + 80 * Math.sin((startAngle - 90) * Math.PI / 180);
            const x2 = 100 + 80 * Math.cos((startAngle + angle - 90) * Math.PI / 180);
            const y2 = 100 + 80 * Math.sin((startAngle + angle - 90) * Math.PI / 180);
            
            const largeArcFlag = angle > 180 ? 1 : 0;
            
            return (
              <path
                key={category.name}
                d={`M 100 100 L ${x1} ${y1} A 80 80 0 ${largeArcFlag} 1 ${x2} ${y2} Z`}
                fill={category.color}
                className="pie-slice"
              >
                <title>{`${category.name}: ${percentage.toFixed(1)}%`}</title>
              </path>
            );
          })}
        </svg>
        <div className="pie-legend">
          {categories.map((category) => {
            const percentage = ((category.amount / total) * 100).toFixed(1);
            return (
              <div key={category.name} className="legend-item">
                <div className="legend-color" style={{ backgroundColor: category.color }}></div>
                <span className="legend-text">{category.name}</span>
                <span className="legend-percentage">{percentage}%</span>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
};

export default ExpenseChart;