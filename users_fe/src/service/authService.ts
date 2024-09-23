import { api } from 'src/boot/axios';

const CONTROLLER = 'auth';

export const authenticateUser = async (username: string, password: string) => {
  try {
    const response = await api.post(`${CONTROLLER}/authenticate`, {username, password});
    return response.data;
  } catch (error) {
    console.error('Error login:', error);
    throw error;
  }
};

export const logoutUser = async () => {
  try {
    const response = await api.get(`${CONTROLLER}/logout`);
    localStorage.removeItem('authData');
    return response.data;
  } catch (error) {
    console.error('Error logout:', error);
    throw error;
  }
};
