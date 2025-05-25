import { BrowserRouter as Router, Routes, Route, Navigate, useLocation } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import Navbar from './components/Navbar';
import Profile from './components/auth/Profile';
import ProtectedRoute from './components/auth/ProtectedRoute';
import Dashboard from './Dashboard';
import './App.css';

const AppRoutes = () => {
  const { isLoading, isAuthenticated, error } = useAuth0();
  const location = useLocation();

  console.log('Auth0 State:', { isLoading, isAuthenticated, error });
  console.log('Current Location:', location);

  if (error) {
    console.error('Auth0 Error:', error);
    return <div>Error: {error.message}</div>;
  }

  if (isLoading) {
    return <div>Cargando...</div>;
  }

  // Si estamos en la ruta /dashboard con parámetros de Auth0
  if (location.pathname === '/dashboard' && (location.search.includes('code=') || location.search.includes('state='))) {
    return <Dashboard />;
  }

  return (
    <Routes>
      <Route path="/" element={<div>Página de inicio</div>} />
      <Route 
        path="/profile" 
        element={
          <ProtectedRoute>
            <Profile />
          </ProtectedRoute>
        } 
      />
      <Route 
        path="/dashboard" 
        element={
          isAuthenticated ? (
            <Dashboard />
          ) : (
            <Navigate to="/" replace />
          )
        } 
      />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

const App = () => {
  return (
    <Router>
      <div className="app">
        <Navbar />
        <main className="main-content">
          <AppRoutes />
        </main>
      </div>
    </Router>
  );
};

export default App;