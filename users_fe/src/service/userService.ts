import { api } from 'src/boot/axios';
import { UsersPayload } from 'src/payload/types';

const CONTROLLER = 'user';

export const getAllUsers = async () => {
  try {
    const response = await api.get(`${CONTROLLER}/index`);
    return response.data;
  } catch (error) {
    console.error('Error fetching users: ', error);
  }
};

export const addUser = async (user: UsersPayload) => {
  try {
    const response = await api.post(`${CONTROLLER}/add`, user);
    return response.data;
  } catch (error: any) {
    console.error('Error adding user: ', error.message);
    throw error;
  }
};

export const updateAuthUser = async (user: UsersPayload) => {
  try {
    const response = await api.put(`${CONTROLLER}/update`, user);
    return response.data;
  } catch (error: any) {
    console.error('Error updating user: ', error.message);
  }
};

export const deleteUser = async (user: UsersPayload) => {
  try {
    const response = await api.delete(`${CONTROLLER}/delete/${user.id}`);
    return response.data;
  } catch (error: any) {
    console.error('Error deleting user: ', error.message);
  }
};
