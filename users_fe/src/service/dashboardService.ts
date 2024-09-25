import { api } from 'src/boot/axios';

const CONTROLLER = 'dashboard';

export const getAuthUser = async () => {
  try {
    const response = await api.get(`${CONTROLLER}/authUser`);
    return response.data;
  } catch (error: any) {
    console.error('Error getting auth user: ', error.message);
  }
};
