import React from 'react';
import { Bar } from 'react-chartjs-2';
import './Dashboard.css';

const MonthlyChart: React.FC = () => {
  const data = {
    labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun'],
    datasets: [
      {
        label: 'Ingresos',
        data: [2400, 2600, 2800, 3000, 3200, 3100],
        backgroundColor: 'rgba(75, 192, 192, 0.6)',
      },
      {
        label: 'Gastos',
        data: [2000, 2200, 2100, 2300, 2500, 2400],
        backgroundColor: 'rgba(255, 99, 132, 0.6)',
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top' as const,
      },
    },
  };

  return (
    <div className="chart-container">
      <h3>Ingresos y Gastos Mensuales</h3>
      <Bar data={data} options={options} />
    </div>
  );
};

export default MonthlyChart;