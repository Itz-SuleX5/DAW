import { useAuth0 } from '@auth0/auth0-react';
import { Navigate } from 'react-router-dom';
import { ReactNode } from 'react';

interface RoleProtectedRouteProps {
  children: ReactNode;
  role: string;
}

const RoleProtectedRoute = ({ children, role }: RoleProtectedRouteProps) => {
  const { isAuthenticated, isLoading, user } = useAuth0();

  if (isLoading) {
    return <div>Cargando...</div>;
  }

  if (!isAuthenticated) {
    return <Navigate to="/" replace />;
  }

  const userRoles = user?.['https://dev-w4kmffqu86f3iz7k.us.auth0.com/roles'] as string[] || [];
  
  if (!userRoles.includes(role)) {
    return <Navigate to="/" replace />;
  }

  return <>{children}</>;
};

export default RoleProtectedRoute; 