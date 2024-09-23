import { createRouter, createWebHistory } from 'vue-router';
import routes from './routes'; // Make sure to point to your routes file

export default createRouter({
  history: createWebHistory(), // Switch to history mode
  routes,
});
