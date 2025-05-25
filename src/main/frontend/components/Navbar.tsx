import { useAuth0 } from '@auth0/auth0-react';
import { Link } from 'react-router-dom';
import LoginButton from './auth/LoginButton';
import LogoutButton from './auth/LogoutButton';
import './Navbar.css';

const Navbar = () => {
  const { isAuthenticated } = useAuth0();

  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <Link to="/">Mi Aplicación</Link>
      </div>
      <div className="navbar-menu">
        <Link to="/" className="navbar-item">Inicio</Link>
        {isAuthenticated && (
          <>
            <Link to="/profile" className="navbar-item">Perfil</Link>
            <Link to="/dashboard" className="navbar-item">Dashboard</Link>
          </>
        )}
      </div>
      <div className="navbar-end">
        {isAuthenticated ? <LogoutButton /> : <LoginButton />}
      </div>
    </nav>
  );
};

export default Navbar; 