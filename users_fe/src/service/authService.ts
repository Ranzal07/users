import { api } from 'src/boot/axios';
import { NewUser } from 'src/payload/types';
import router from 'src/router';

const CONTROLLER = 'auth';

export const addNewUser = async (newUser: NewUser) => {
  try {
    await api.post(`${CONTROLLER}/signup`, newUser);
  } catch (error: any) {
    console.error('Error signup: ', error.message);
    throw error;
  }
};

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
