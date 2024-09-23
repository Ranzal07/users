import { api } from 'src/boot/axios';

const CONTROLLER = 'dashboard';

export const getAuthUser = async () => {
  try {
    const response = await api.get(`${CONTROLLER}/index`);
    return response.data;
  } catch (error) {
    console.error('Error dashboard:', error);
    throw error;
  }
};
