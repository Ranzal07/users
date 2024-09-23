import { api, axios } from 'src/boot/axios';

const CONTROLLER = 'auth';

// Function to get the expiration time of the token
const getTokenExpiration = (token: string) => {
  const payload = JSON.parse(atob(token.split('.')[1]));
  return payload.exp * 1000; // Convert to milliseconds
};

// src/router/authGuard.ts
export function useAuthGuard() {
  return async (_to: any, _from: any, next: any) => {
    const authData = localStorage.getItem('authData');

    if (!authData) {
      return next('/v1/auth/login');
    }

    const { token, refreshToken } = JSON.parse(authData);
    const expirationTime = getTokenExpiration(token);
    const currentTime = Date.now();

    // Check if the token is about to expire in 5 seconds
    if (expirationTime - currentTime < 5000) {
      try {
        const refreshResponse = await api.post(`${CONTROLLER}/refreshToken`, refreshToken);
        if (refreshResponse.status === 200) {
          // Update local storage with new tokens
          localStorage.setItem('authData', JSON.stringify(refreshResponse.data));
        } else {
          console.error('Refresh token failed:', refreshResponse.data);
          localStorage.removeItem('authData');
          return next('/v1/auth/login');
        }
      } catch (refreshError) {
        console.error('Refresh token request failed:', refreshError);
        localStorage.removeItem('authData');
        return next('/v1/auth/login');
      }
    }

    // Now verify the token
    try {
      const response = await api.post(`${CONTROLLER}/verifyToken`, {}, {
        headers: {
          'Authorization': `Bearer ${token}`,
        }
      });

      if (response.status === 200) {
        return next();
      } else {
        console.log('Token verification failed, redirecting to login.');
        localStorage.removeItem('authData');
        return next('/v1/auth/login');
      }
    } catch (error: unknown) {
      if (axios.isAxiosError(error) && error.response) {
        console.error('Token verification failed:', error.response.data);
      } else if (error instanceof Error) {
        console.error('Token verification failed:', error.message);
      } else {
        console.error('An unknown error occurred');
      }
      localStorage.removeItem('authData');
      return next('/v1/auth/login');
    }
  };
}
