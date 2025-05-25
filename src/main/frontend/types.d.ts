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