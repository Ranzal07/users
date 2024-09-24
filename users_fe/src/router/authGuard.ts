import { axios, api } from 'src/boot/axios';

const CONTROLLER = 'auth';

export function useAuthGuard() {
  return async (_to: any, _from: any, next: any) => {
    try {
      const refreshResponse = await api.post(`${CONTROLLER}/refreshToken`);
      console.log(refreshResponse.data);
      next();
    } catch (error: unknown) {
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
