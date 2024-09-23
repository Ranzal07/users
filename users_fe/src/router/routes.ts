import { RouteRecordRaw } from 'vue-router';
import { useAuthGuard } from './authGuard';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    redirect: '/v1/auth/login',
  },
  {
    path: '/v1/auth/login',
    component: () => import('pages/AuthPage.vue'),
  },
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      {
        path: '/v1/dashboard/index',
        component: () => import('pages/DashboardPage.vue'),
        beforeEnter: useAuthGuard(),
      },
      {
        path: '/v1/user/index',
        component: () => import('pages/UserPage.vue'),
        beforeEnter: useAuthGuard(),
      },
      {
        path: '/v1/employment/index',
        component: () => import('pages/EmploymentPage.vue'),
        beforeEnter: useAuthGuard(),
      },
      {
        path: '/v1/level/index',
        component: () => import('pages/LevelPage.vue'),
        beforeEnter: useAuthGuard(),
      },
      {
        path: '/v1/position/index',
        component: () => import('pages/PositionPage.vue'),
        beforeEnter: useAuthGuard(),
      },
      {
        path: '/v1/account/index',
        component: () => import('pages/AccountPage.vue'),
        beforeEnter: useAuthGuard(),
      },
    ],
  },

  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue'),
  },
];

export default routes;
