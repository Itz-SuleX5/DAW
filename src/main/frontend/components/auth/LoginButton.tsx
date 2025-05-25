import { useAuth0 } from '@auth0/auth0-react';
import { useLocation } from 'react-router-dom';
import './LoginButton.css';

const LoginButton = () => {
  const { loginWithRedirect } = useAuth0();
  const location = useLocation();
  const from = location.state?.from?.pathname || '/dashboard';

  return (
    <button
      className="login-button"
      onClick={() => loginWithRedirect({
        appState: { returnTo: from }
      })}
    >
      Iniciar Sesión
    </button>
  );
};

export default LoginButton; 