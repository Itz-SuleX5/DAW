import React from 'react';
import './Dashboard.css';

interface MetricCardProps {
  title: string;
  value: string | number;
  type?: 'success' | 'danger' | 'info';
}

const MetricCard: React.FC<MetricCardProps> = ({ title, value, type }) => {
  const cardClass = type ? `metric-card ${type}` : 'metric-card';

  return (
    <div className={cardClass}>
      <h3>{title}</h3>
      <p>{value}</p>
    </div>
  );
};

export default MetricCard;
