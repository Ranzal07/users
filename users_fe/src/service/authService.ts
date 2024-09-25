import { api } from 'src/boot/axios';
import router from 'src/router';

const CONTROLLER = 'auth';

export const authenticateUser = async (username: string, password: string) => {
  try {
    await api.post(`${CONTROLLER}/authenticate`, { username, password });
  } catch (error: any) {
    console.error('Error login: ', error.message);
    throw error;
  }
};

export const logoutUser = async () => {
  try {
    const response = await api.get(`${CONTROLLER}/logout`);
    console.log(response.data);
    router.push('/v1/auth/login');
  } catch (error: any) {
    console.error('Error logout: ', error.message);
    throw error;
  }
};
