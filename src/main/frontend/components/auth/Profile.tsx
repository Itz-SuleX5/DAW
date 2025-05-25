import { useAuth0 } from '@auth0/auth0-react';
import './Auth.css';

const Profile = () => {
  const { user, isAuthenticated } = useAuth0();

  if (!isAuthenticated) {
    return null;
  }

  return (
    <div className="profile-container">
      {user?.picture && (
        <img 
          src={user.picture} 
          alt={user.name || 'Profile'} 
          className="profile-picture"
        />
      )}
      <h2 className="profile-name">{user?.name}</h2>
      <p className="profile-email">{user?.email}</p>
    </div>
  );
};

export default Profile; 