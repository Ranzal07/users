import { api } from 'src/boot/axios';

const CONTROLLER = 'auth';

export const authenticateUser = async (username: string, password: string) => {
  try {
    await api.post(`${CONTROLLER}/authenticate`, { username, password });
  } catch (error) {
    console.error('Error login:', error);
    throw error;
  }
};

export const getAuthUser = async () => {
  try {
    const response = await api.get(`${CONTROLLER}/authUser`);
    return response.data;
  } catch (error) {
    console.error('Error fetching the Auth users:', error);
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
