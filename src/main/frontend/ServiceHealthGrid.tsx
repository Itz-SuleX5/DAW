import React from 'react';
import './Dashboard.css';

const ServiceHealthGrid: React.FC = () => {
  const services = [
    { name: 'Supermercado Local', status: 'Activo' },
    { name: 'Pago Mensual', status: 'Completado' },
    { name: 'Gasolina', status: 'Pendiente' },
    { name: 'Internet', status: 'Activo' },
    { name: 'Proyecto Diseño', status: 'Completado' },
  ];

  return (
    <div className="service-grid">
      <h3>Estado de Servicios</h3>
      <table>
        <thead>
          <tr>
            <th>Servicio</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {services.map((service, index) => (
            <tr key={index}>
              <td>{service.name}</td>
              <td>{service.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ServiceHealthGrid;