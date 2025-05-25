import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import Navbar from './components/Navbar';
import Profile from './components/auth/Profile';
import ProtectedRoute from './components/auth/ProtectedRoute';
import RoleProtectedRoute from './components/auth/RoleProtectedRoute';
import './App.css';

const App = () => {
  const { isLoading, isAuthenticated } = useAuth0();

  if (isLoading) {
    return <div>Cargando...</div>;
  }

  return (
    <Router>
      <div className="app">
        <Navbar />
        <main className="main-content">
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
                  <RoleProtectedRoute role="admin">
                    <div>Dashboard (solo para administradores)</div>
                  </RoleProtectedRoute>
                ) : (
                  <Navigate to="/" replace />
                )
              } 
            />
            <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
        </main>
      </div>
    </Router>
  );
};

export default App;