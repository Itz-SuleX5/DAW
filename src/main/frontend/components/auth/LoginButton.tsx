import { useAuth0 } from '@auth0/auth0-react';
import './Auth.css';

const LoginButton = () => {
  const { loginWithRedirect, error } = useAuth0();

  const handleLogin = async () => {
    try {
      console.log('Iniciando login...');
      const baseUrl = "https://obscure-space-guacamole-q7qg9q77jj7g29qjq-8080.app.github.dev";
      await loginWithRedirect({
        authorizationParams: {
          redirect_uri: `${baseUrl}/dashboard`
        }
      });
    } catch (err) {
      console.error('Error durante el login:', err);
    }
  };

  if (error) {
    console.error('Auth0 Error en LoginButton:', error);
    return <div>Error: {error.message}</div>;
  }

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