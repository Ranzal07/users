import { axios, api } from 'src/boot/axios';
import { Router } from 'vue-router';

const CONTROLLER = 'auth';

export function useAuthGuard() {
  return async (_to: any, _from: any, next: any) => {
    try {
      await api.post(`${CONTROLLER}/refreshToken`);
      next();
    } catch (error: any) {
      if (axios.isAxiosError(error) && error.response) {
        console.error(error.response.data);
      } else if (error instanceof Error) {
        console.error('Refresh token request failed: ', error.message);
      } else {
        console.error('An unknown error occurred');
      }
      next('/v1/auth/login');
    }
  };
}

export const isValidToken = async (router: Router) => {
  try {
    await api.post(`${CONTROLLER}/refreshToken`);
    return true;
  } catch (error: any) {
    if (axios.isAxiosError(error) && error.response) {
      console.error(error.response.data);
    } else if (error instanceof Error) {
      console.error('Refresh token request failed: ', error.message);
    } else {
      console.error('An unknown error occurred');
    }
    router.push('/v1/auth/login');
    return false;
  }
};