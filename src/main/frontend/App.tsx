import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import Navbar from './components/Navbar';
import Dashboard from './Dashboard';
import './App.css';

function App() {
  const { isAuthenticated, isLoading } = useAuth0();

  if (isLoading) {
    return <div>Cargando...</div>;
  }

  return (
    <Router>
      <div className="app">
        <Navbar />
        <main className="main-content">
          <Routes>
            <Route 
              path="/*" 
              element={isAuthenticated ? <Dashboard /> : <Navigate to="/login" replace />} 
            />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;