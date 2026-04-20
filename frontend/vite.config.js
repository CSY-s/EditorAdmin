import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig(({ command }) => ({
  base: command === 'build' ? '/front/' : '/',
  plugins: [vue()],
  server: {
    port: 5173,
    open: true,
    proxy: {
      '/editorial-office': {
        target: 'http://localhost:2008',
        changeOrigin: true
      },
      '/upload': {
        target: 'http://localhost:2008',
        changeOrigin: true
      },
      '/download': {
        target: 'http://localhost:2008',
        changeOrigin: true
      },
      '/api/plagiarism': {
        target: 'http://localhost:2008',
        changeOrigin: true
      }
    }
  },
  build: {
    outDir: '../src/main/resources/static/front',
    emptyOutDir: true,
    sourcemap: true
  }
}))
