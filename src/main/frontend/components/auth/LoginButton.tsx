import { useAuth0 } from '@auth0/auth0-react';
import './Auth.css';

const LoginButton = () => {
  const { loginWithRedirect } = useAuth0();

  const handleLogin = () => {
    loginWithRedirect({
      authorizationParams: {
        redirect_uri: `${window.location.origin}/dashboard`
      }
    });
  };

  return (
    <button 
      className="auth-button login"
      onClick={handleLogin}
    >
      Iniciar sesión
    </button>
  );
};

export default LoginButton; 