import { useAuth0 } from '@auth0/auth0-react';
import { Navigate } from 'react-router-dom';
import Dashboard from './Dashboard';

function App() {
  const { isAuthenticated, isLoading, loginWithRedirect } = useAuth0();

  if (isLoading) {
    return <div>Cargando...</div>;
  }

  if (!isAuthenticated) {
    loginWithRedirect();
    return <div>Redirigiendo al login...</div>;
  }

  return <Dashboard />;
}

export default App;
export default App;