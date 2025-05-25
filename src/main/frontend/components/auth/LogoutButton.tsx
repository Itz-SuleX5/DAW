import { useAuth0 } from '@auth0/auth0-react';
import './Auth.css';

const LogoutButton = () => {
  const { logout } = useAuth0();

  const handleLogout = () => {
    logout({
      logoutParams: {
        returnTo: window.location.origin
      }
    });
  };

  return (
    <button 
      className="auth-button logout"
      onClick={handleLogout}
    >
      Cerrar sesión
    </button>
  );
};

export default LogoutButton; 