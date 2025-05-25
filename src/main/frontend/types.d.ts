import { AppState } from '@auth0/auth0-react';

declare global {
  interface Window {
    ReactDashboard: {
      render: (container: HTMLElement) => void;
    };
  }
}

export interface Auth0AppState extends AppState {
  returnTo?: string;
}

declare module '@auth0/auth0-react' {
  export interface Auth0ContextInterface {
    isAuthenticated: boolean;
    isLoading: boolean;
    loginWithRedirect: () => void;
  }

  export function useAuth0(): Auth0ContextInterface;

  export interface Auth0ProviderProps {
    domain: string;
    clientId: string;
    authorizationParams: {
      redirect_uri: string;
      audience?: string;
      scope?: string;
    };
    useRefreshTokens?: boolean;
    cacheLocation?: 'memory' | 'localstorage';
  }

  export const Auth0Provider: React.FC<Auth0ProviderProps>;
} 