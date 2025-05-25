import React from 'react';
import ReactDOM from 'react-dom/client';
import { Auth0Provider } from '@auth0/auth0-react';
import App from './App';
import './index.css';

// Check if we're in a Vaadin context or standalone React
const isVaadinContext = window.location.pathname.includes('/vaadin');

if (!isVaadinContext) {
  // Standalone React mode
  const container = document.getElementById('outlet') || document.getElementById('root');
  if (container) {
    const root = ReactDOM.createRoot(container);
    const domain = "dev-6a8gx4jqe8ymcodi.us.auth0.com";
    const clientId = "LeECmGtmibebqZVG80hUoUUl7ZefIr7a";
    const audience = "https://dev-6a8gx4jqe8ymcodi.us.auth0.com/api/v2/";
    
    // Usar la URL base correcta
    const baseUrl = window.location.origin;
    const redirectUri = `${baseUrl}/dashboard`;

    console.log('Auth0 Configuration:', {
      domain,
      clientId,
      redirectUri,
      currentUrl: window.location.href,
      origin: window.location.origin,
      pathname: window.location.pathname,
      search: window.location.search
    });

    root.render(
      <React.StrictMode>
        <Auth0Provider
          domain={domain}
          clientId={clientId}
          authorizationParams={{
            redirect_uri: redirectUri,
            audience: audience,
            scope: "openid profile email"
          }}
          useRefreshTokens={true}
          cacheLocation="localstorage"
          onRedirectCallback={(appState) => {
            console.log('Redirect Callback:', {
              appState,
              currentUrl: window.location.href,
              search: window.location.search
            });
            // Limpiar los parámetros de la URL después de la redirección
            if (window.location.search) {
              window.history.replaceState(
                {},
                document.title,
                window.location.pathname
              );
            }
          }}
        >
          <App />
        </Auth0Provider>
      </React.StrictMode>
    );
  }
} else {
  // Export for Vaadin integration
  window.ReactDashboard = {
    render: (container: HTMLElement) => {
      const root = ReactDOM.createRoot(container);
      const domain = "dev-6a8gx4jqe8ymcodi.us.auth0.com";
      const clientId = "LeECmGtmibebqZVG80hUoUUl7ZefIr7a";
      const audience = "https://dev-6a8gx4jqe8ymcodi.us.auth0.com/api/v2/";
      
      // Usar la URL base correcta
      const baseUrl = window.location.origin;
      const redirectUri = `${baseUrl}/dashboard`;

      root.render(
        <React.StrictMode>
          <Auth0Provider
            domain={domain}
            clientId={clientId}
            authorizationParams={{
              redirect_uri: redirectUri,
              audience: audience,
              scope: "openid profile email"
            }}
            useRefreshTokens={true}
            cacheLocation="localstorage"
            onRedirectCallback={(appState) => {
              console.log('Redirect Callback:', {
                appState,
                currentUrl: window.location.href,
                search: window.location.search
              });
              // Limpiar los parámetros de la URL después de la redirección
              if (window.location.search) {
                window.history.replaceState(
                  {},
                  document.title,
                  window.location.pathname
                );
              }
            }}
          >
            <App />
          </Auth0Provider>
        </React.StrictMode>
      );
    }
  };
}

// Export components for potential use in Vaadin views
export { default as Dashboard } from './Dashboard';
export { default as App } from './App';