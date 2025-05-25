import React from 'react';
import { Doughnut } from 'react-chartjs-2';
import './Dashboard.css';

const ResponseTimeChart: React.FC = () => {
  const data = {
    labels: ['Alimentación', 'Transporte', 'Servicios', 'Entretenimiento', 'Otros'],
    datasets: [
      {
        data: [500, 300, 200, 150, 100],
        backgroundColor: [
          'rgba(255, 99, 132, 0.6)',
          'rgba(54, 162, 235, 0.6)',
          'rgba(255, 206, 86, 0.6)',
          'rgba(75, 192, 192, 0.6)',
          'rgba(153, 102, 255, 0.6)',
        ],
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
    },
  };

  return (
    <div className="chart-container">
      <h3>Distribución de Gastos</h3>
      <Doughnut data={data} options={options} />
    </div>
  );
};

export default ResponseTimeChart;