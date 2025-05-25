import { UserConfigFn } from 'vite';
import { overrideVaadinConfig } from './vite.generated';

const customConfig: UserConfigFn = () => ({
  // Here you can add custom Vite parameters
  // https://vitejs.dev/config/
  server: {
    host: '0.0.0.0',
    port: 8080,
    strictPort: true,
    hmr: {
      protocol: 'ws',
      host: '0.0.0.0',
      port: 8080
    }
  }
});

export default overrideVaadinConfig(customConfig);
