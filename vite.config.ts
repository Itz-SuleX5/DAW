import { UserConfigFn } from 'vite';
import { overrideVaadinConfig } from './vite.generated';

const customConfig: UserConfigFn = (env) => ({
  // Here you can add custom Vite parameters
  // https://vitejs.dev/config/
  server: {
    host: '0.0.0.0',
    port: 8080,
    strictPort: true,
    hmr: {
      protocol: 'wss',
      host: 'obscure-space-guacamole-q7qg9q77jj7g29qjq-8080.app.github.dev',
      port: 443,
      clientPort: 443
    }
  }
});

export default overrideVaadinConfig(customConfig);
